package tvz.lukec.autoservice.rest.domain.form

import tvz.lukec.autoservice.model.ServiceStatus
import java.math.BigDecimal

class EditServiceForm {
    var id: Long? = null
    var name: String? = null
    var price: BigDecimal? = null
    var description: String? = null
    var mileage: Int? = null
    var status: ServiceStatus? = null
}