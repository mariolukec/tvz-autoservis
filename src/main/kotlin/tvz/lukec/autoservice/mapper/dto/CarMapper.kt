package tvz.lukec.autoservice.mapper.dto

import org.springframework.stereotype.Component
import tvz.lukec.autoservice.model.Car
import tvz.lukec.autoservice.repository.CarRepository
import tvz.lukec.autoservice.rest.domain.dto.CarDto

@Component
class CarMapper(
        private val carRepository: CarRepository
) {
    fun toDto(car: Car): CarDto {
        return CarDto().also {
            it.id = car.id
            it.image = car.image
            it.make = car.make
            it.model = car.model
            it.year = car.year
            it.registrationPlate = car.registrationPlate
            it.serviceCount = carRepository.getServiceCount(car.id!!)
            it.serviceTotalCost = carRepository.getServiceTotalSum(car.id!!)
            it.lastServiceAt = carRepository.getLastServiceAt(car.id!!)
        }
       
    }

    fun toDtos(cars: List<Car>): List<CarDto> {
        return cars.map { toDto(it) }
    }
}