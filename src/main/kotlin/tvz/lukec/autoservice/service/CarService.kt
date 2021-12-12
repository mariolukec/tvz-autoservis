package tvz.lukec.autoservice.service

import tvz.lukec.autoservice.rest.domain.dto.CarDto
import tvz.lukec.autoservice.rest.domain.form.CarForm
import tvz.lukec.autoservice.rest.domain.form.EditCarForm


interface CarService {
    fun getMineCars(userEmail: String): List<CarDto>
    fun save(carForm: CarForm): CarDto
    fun update(editCarForm: EditCarForm): CarDto
    fun delete(id: Long): Boolean
    fun getCostPerMonth(carId: Long, year: Long): List<Double>
    fun getCarsByClient(clientId: Long): List<CarDto>
}
