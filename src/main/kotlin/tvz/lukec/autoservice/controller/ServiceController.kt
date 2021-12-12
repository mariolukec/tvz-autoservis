package tvz.lukec.autoservice.controller

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tvz.lukec.autoservice.model.Image
import tvz.lukec.autoservice.model.ServiceStatus
import tvz.lukec.autoservice.rest.domain.dto.ServiceDto
import tvz.lukec.autoservice.rest.domain.filter.ServiceFilter
import tvz.lukec.autoservice.rest.domain.form.EditServiceForm
import tvz.lukec.autoservice.rest.domain.form.ServiceForm
import tvz.lukec.autoservice.service.ImageService
import tvz.lukec.autoservice.service.ServiceService
import java.security.Principal
import java.time.LocalDateTime

@RestController
@RequestMapping(value = ["/service"])
class ServiceController(
        private val serviceService: ServiceService,
        private val imageService: ImageService
) {
    private val LOG = LoggerFactory.getLogger(ServiceController::class.java)


    @GetMapping("/active")
    fun getAllActive(): ResponseEntity<List<ServiceDto>> {
        LOG.info("Getting all active services")
        return ResponseEntity(serviceService.getAllActiveServices(), HttpStatus.OK)
    }

    @PostMapping
    fun getAll(@RequestBody serviceFilter: ServiceFilter, pageable: Pageable): ResponseEntity<Page<ServiceDto>> {
        LOG.info("Getting all services")
        return ResponseEntity(serviceService.getAllServices(serviceFilter, pageable!!), HttpStatus.OK)
    }

    @PostMapping("/new")
    fun save(@RequestBody serviceForm: ServiceForm): ResponseEntity<ServiceDto> {
        LOG.info("Saving new service for carId = " + serviceForm.carId)
        val newService: ServiceDto = serviceService.save(serviceForm)
        return ResponseEntity<ServiceDto>(newService, HttpStatus.ACCEPTED)
    }

    @GetMapping("/mine")
    fun getMyServices(principal: Principal): ResponseEntity<List<ServiceDto>> {
        LOG.info("Fetching services for user with email = " + principal.name)
        val servicesDtos: List<ServiceDto> = serviceService.getMyServices(principal.name)
        return ResponseEntity<List<ServiceDto>>(servicesDtos, HttpStatus.OK)
    }

    @GetMapping("/user/{userId}")
    fun getServicesByUser(@PathVariable userId: Long): ResponseEntity<List<ServiceDto>> {
        LOG.info("Fetching services for user with id = $userId")
        val servicesDtos: List<ServiceDto> = serviceService.getUserServices(userId)
        return ResponseEntity<List<ServiceDto>>(servicesDtos, HttpStatus.OK)
    }

    @GetMapping("/car/{carId}")
    fun getServicesByCarId(@PathVariable carId: Long): ResponseEntity<List<ServiceDto>> {
        LOG.info("Fetching services for car with id = $carId")
        val servicesDtos: List<ServiceDto> = serviceService.getCarServices(carId)
        return ResponseEntity<List<ServiceDto>>(servicesDtos, HttpStatus.OK)
    }

    @GetMapping("/{serviceId}")
    fun getById(@PathVariable serviceId: Long): ResponseEntity<ServiceDto> {
        LOG.info("Getting service with id = $serviceId")
        return ResponseEntity(serviceService.getById(serviceId), HttpStatus.OK)
    }

    @DeleteMapping("/{serviceId}")
    fun delete(@PathVariable serviceId: Long): ResponseEntity<Boolean> {
        LOG.info("Deleting service with id = $serviceId")
        val deleted = serviceService.delete(serviceId)
        return ResponseEntity(deleted, HttpStatus.OK)
    }

    @PutMapping("/update")
    fun update(@RequestBody editServiceForm: EditServiceForm): ResponseEntity<ServiceDto> {
        LOG.info("Editing service with id" + editServiceForm.id)
        val serviceDto: ServiceDto = serviceService.update(editServiceForm)
        return ResponseEntity<ServiceDto>(serviceDto, HttpStatus.OK)
    }

    @PatchMapping("/update/service-status/{serviceId}/{serviceStatus}")
    fun updateServiceStatus(@PathVariable serviceId: Long, @PathVariable serviceStatus: ServiceStatus): ResponseEntity<ServiceDto> {
        LOG.info("Updating service status for service with id = $serviceId")
        val updated: ServiceDto = serviceService.updateServiceStatus(serviceId, serviceStatus)
        return ResponseEntity<ServiceDto>(updated, HttpStatus.OK)
    }


    @PatchMapping("/picked-up/{serviceId}")
    fun setPickedUp(@PathVariable serviceId: Long): ResponseEntity<ServiceDto> {
        val now = LocalDateTime.now()
        LOG.info("Service with id = $serviceId PICKED UP AT $now")
        val updated: ServiceDto = serviceService.setPickedUpAt(serviceId, now)
        return ResponseEntity<ServiceDto>(updated, HttpStatus.OK)
    }


    @PostMapping("/images/{serviceId}")
    fun saveImage(@RequestPart imagesData: List<MultipartFile>, @PathVariable serviceId: Long): ResponseEntity<List<Image>> {
        LOG.info("Adding images for service with id = $serviceId")
        val images = imageService.saveServiceImages(serviceId, imagesData)
                ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(images, HttpStatus.ACCEPTED)
    }

    @PostMapping("{serviceId}/mechanics/add")
    fun setServiceMechanics(@PathVariable serviceId: Long, @RequestBody mechanicIdsList: List<Long>): ResponseEntity<ServiceDto> {
        LOG.info("Adding mechanic/s for service with id = $serviceId")
        val updated: ServiceDto = serviceService.saveMechanics(serviceId, mechanicIdsList)
        return ResponseEntity<ServiceDto>(updated, HttpStatus.OK)
    }

    @DeleteMapping("{serviceId}/mechanics/delete")
    fun removeMechanicsFromService(@PathVariable serviceId: Long, @RequestBody mechanicIdsList: List<Long>): ResponseEntity<ServiceDto> {
        LOG.info("Deleting mechanic/s for service with id = $serviceId")
        val updated: ServiceDto = serviceService.removeMechanics(serviceId, mechanicIdsList)
        return ResponseEntity<ServiceDto>(updated, HttpStatus.OK)
    }
}