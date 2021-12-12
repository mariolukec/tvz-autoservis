package tvz.lukec.autoservice.rest.domain.dto

import tvz.lukec.autoservice.model.Image

class CarDto {
    var id: Long? = null
    var image: Image? = null
    var make: String? = null
    var model: String? = null
    var year: Long? = null
    var serviceCount: Long? = null
    var serviceTotalCost: Double? = null
    var lastServiceAt: Int? = null
    var registrationPlate: String? = null
}