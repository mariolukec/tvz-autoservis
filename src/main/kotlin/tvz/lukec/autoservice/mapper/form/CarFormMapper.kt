package tvz.lukec.autoservice.mapper.form

import org.springframework.stereotype.Component
import tvz.lukec.autoservice.model.Car
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.rest.domain.form.CarForm

@Component
class CarFormMapper(
        private val userRepository: UserRepository
) {

    fun toEntity(carForm: CarForm): Car {
        val car = Car()
        car.make = carForm.make
        car.model = carForm.model
        car.year = carForm.year
        car.registrationPlate = carForm.registrationPlate
        val user: User = userRepository.getById(carForm.userId!!)
        car.user = user

        return car
    }
}