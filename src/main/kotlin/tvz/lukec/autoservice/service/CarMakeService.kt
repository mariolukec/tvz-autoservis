package tvz.lukec.autoservice.service

import tvz.lukec.autoservice.rest.domain.dto.CarMakeDto

interface CarMakeService {
    val allMakes: List<CarMakeDto>
}
