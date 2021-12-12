package tvz.lukec.autoservice.mapper.form

import org.springframework.stereotype.Component
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.repository.CarRepository
import tvz.lukec.autoservice.rest.domain.form.ServiceForm
import java.time.LocalDateTime


@Component
class ServiceFormMapper(
        private val carRepository: CarRepository
) {
    fun toEntity(serviceForm: ServiceForm): Service {
        val service = Service()
        service.status = serviceForm.serviceStatus
        service.car = carRepository.getById(serviceForm.carId!!)
        service.name = serviceForm.name
        service.price = serviceForm.price
        service.description = serviceForm.description
        service.mileage = serviceForm.mileage
        service.dateReceived = LocalDateTime.now()

        return service
    }
}
