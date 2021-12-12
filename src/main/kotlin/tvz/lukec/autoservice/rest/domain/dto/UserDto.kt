package tvz.lukec.autoservice.rest.domain.dto

import tvz.lukec.autoservice.model.Image

class UserDto {
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var image: Image? = null
    var carCount: Long? = null
    var serviceCount: Int? = null
    var role: String? = null
}