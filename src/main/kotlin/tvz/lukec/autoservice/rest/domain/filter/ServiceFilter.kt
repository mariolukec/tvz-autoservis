package tvz.lukec.autoservice.rest.domain.filter

import tvz.lukec.autoservice.model.ServiceStatus
import java.time.LocalDateTime

class ServiceFilter {
    var mechanicId: Long? = null
    var serviceStatus: ServiceStatus? = null
    var from: LocalDateTime? = null
    var to: LocalDateTime? = null
}