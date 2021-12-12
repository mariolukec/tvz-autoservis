package tvz.lukec.autoservice.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tvz.lukec.autoservice.model.Image
import tvz.lukec.autoservice.rest.domain.dto.CarDto
import tvz.lukec.autoservice.rest.domain.dto.CarMakeDto
import tvz.lukec.autoservice.rest.domain.dto.CarModelDto
import tvz.lukec.autoservice.rest.domain.dto.CarStatsDto
import tvz.lukec.autoservice.rest.domain.form.CarForm
import tvz.lukec.autoservice.rest.domain.form.EditCarForm
import tvz.lukec.autoservice.service.CarMakeService
import tvz.lukec.autoservice.service.CarModelService
import tvz.lukec.autoservice.service.CarService
import tvz.lukec.autoservice.service.ImageService
import java.security.Principal

@RestController
@RequestMapping(value = ["/cars"])
class CarController(
        private val carService: CarService,
        private val imageService: ImageService,
        private val carMakeService: CarMakeService,
        private val carModelService: CarModelService
) {

    private val LOG = LoggerFactory.getLogger(CarController::class.java)


    @GetMapping("/client/{clientId}")
    fun getClientCars(@PathVariable clientId: Long): ResponseEntity<List<CarDto>> {
        LOG.info("GETTING CARS FOR CLIENT$clientId")
        return ResponseEntity(carService.getCarsByClient(clientId), HttpStatus.OK)
    }


    @GetMapping("/mine")
    fun getMineCars(principal: Principal): ResponseEntity<List<CarDto>> {
        LOG.info("Fetching cars for user with email = " + principal.name)
        val cars: List<CarDto> = carService.getMineCars(principal.name)
        return ResponseEntity<List<CarDto>>(cars, HttpStatus.OK)
    }

    @PostMapping("/new")
    fun save(@RequestBody carForm: CarForm): ResponseEntity<CarDto> {
        LOG.info("Saving new car for userId = " + carForm.userId)
        val newCar: CarDto = carService.save(carForm)
        return ResponseEntity<CarDto>(newCar, HttpStatus.ACCEPTED)
    }

    @PostMapping("/update")
    fun update(@RequestBody editCarForm: EditCarForm): ResponseEntity<CarDto> {
        LOG.info("UPDATING CAR with id = " + editCarForm.id)
        val car: CarDto = carService.update(editCarForm)
        return ResponseEntity<CarDto>(car, HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{carId}")
    fun delete(@PathVariable carId: Long): ResponseEntity<Boolean> {
        LOG.info("DELETING car with id = $carId")
        val deleted: Boolean = carService.delete(carId)
        return ResponseEntity(deleted, HttpStatus.OK)
    }

    @PostMapping("/image/{carId}")
    fun saveImage(@RequestPart imageData: MultipartFile, @PathVariable carId: Long): ResponseEntity<Image> {
        LOG.info("SAVING CAR IMAGE for car with id = $carId")
        val image: Image = imageService.saveCarImage(carId, imageData)
                ?: return ResponseEntity<Image>(HttpStatus.BAD_REQUEST)
        return ResponseEntity<Image>(image, HttpStatus.ACCEPTED)
    }

    @GetMapping("/makes")
    fun getCarMakes(): ResponseEntity<List<CarMakeDto>> {
        return ResponseEntity(carMakeService.allMakes, HttpStatus.OK)
    }


    @GetMapping("/models")
    fun getCarModels(@RequestParam("makeId") makeId: Long): ResponseEntity<List<CarModelDto>> {
        return ResponseEntity(carModelService.getAllModelsByMake(makeId), HttpStatus.OK)
    }

    @GetMapping("/stats/{carId}/{year}")
    fun getCostPerMonthForYear(@PathVariable carId: Long, @PathVariable year: Long): ResponseEntity<CarStatsDto> {
        LOG.info("FETCHING service stats for car with id = $carId for year = $year")
        val values: List<Double> = carService.getCostPerMonth(carId, year)
        val carStatsDto = CarStatsDto().also {
            it.carId = carId
            it.servicesCosts = values
        }

        return ResponseEntity<CarStatsDto>(carStatsDto, HttpStatus.OK)
    }

}