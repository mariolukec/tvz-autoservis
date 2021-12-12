package tvz.lukec.autoservice.service.impl

import org.springframework.stereotype.Service
import tvz.lukec.autoservice.mapper.form.CarFormMapper
import tvz.lukec.autoservice.mapper.dto.CarMapper
import tvz.lukec.autoservice.repository.CarRepository
import tvz.lukec.autoservice.repository.ServiceRepository
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.rest.domain.dto.CarDto
import tvz.lukec.autoservice.rest.domain.form.CarForm
import tvz.lukec.autoservice.rest.domain.form.EditCarForm
import tvz.lukec.autoservice.service.CarService

@Service
class CarServiceImpl(
        private val carRepository: CarRepository,
        private val userRepository: UserRepository,
        private val carFormMapper: CarFormMapper,
        private val carMapper: CarMapper,
        private val serviceRepository: ServiceRepository
) : CarService {
    override fun getMineCars(userEmail: String): List<CarDto> {
        val cars = carRepository.getAllByUserEmail(userEmail)
        return carMapper.toDtos(cars)
    }

    override fun save(carForm: CarForm): CarDto {
        val car = carFormMapper.toEntity(carForm)
        return carMapper.toDto(carRepository.save(car))
    }

    override fun update(editCarForm: EditCarForm): CarDto {
        val car = carRepository.getById(editCarForm.id!!)
        car.make = editCarForm.make
        car.model = editCarForm.model
        car.year = editCarForm.year
        car.registrationPlate = editCarForm.registrationPlate
        if(car.user?.id != editCarForm.userId){
            val newUser = userRepository.getById(editCarForm.userId!!)
            car.user = newUser
        }

        return carMapper.toDto(carRepository.save(car))
    }

    override fun delete(id: Long): Boolean {
        carRepository.deleteById(id)
        return !carRepository.existsById(id)
    }

    override fun getCostPerMonth(carId: Long, year: Long): List<Double> {
        val values = mutableListOf<Double>()

        for (i in 1..12) {
            val monthSum: Double = serviceRepository.getCostForMonth(i, year, carId)
            values.add(monthSum)
        }

        return values
    }

    override fun getCarsByClient(clientId: Long): List<CarDto> {
        return carMapper.toDtos(carRepository.getAllByUserId(clientId))
    }
}