package tvz.lukec.autoservice.rest.domain.form

import tvz.lukec.autoservice.model.ServiceStatus
import java.math.BigDecimal

class ServiceForm {
    var carId: Long? = null
    var name: String? = null
    var price: BigDecimal? = null
    var description: String? = null
    var mileage: Int? = null
    var serviceStatus: ServiceStatus? = null
}