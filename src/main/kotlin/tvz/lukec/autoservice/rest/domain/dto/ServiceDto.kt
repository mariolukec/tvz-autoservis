package tvz.lukec.autoservice.rest.domain.dto

import tvz.lukec.autoservice.model.Image
import tvz.lukec.autoservice.model.ServiceStatus
import java.math.BigDecimal
import java.time.LocalDateTime

class ServiceDto {
    var id: Long? = null
    var car: CarDto? = null
    var name: String? = null
    var price: BigDecimal? = null
    var description: String? = null
    var mileage: Int? = null
    var dateReceived: LocalDateTime? = null
    var dateFinished: LocalDateTime? = null
    var status: ServiceStatus? = null
    var images: List<Image>? = null
    var mechanics: List<UserDto>? = null
    var pickedUpAt: LocalDateTime? = null
}