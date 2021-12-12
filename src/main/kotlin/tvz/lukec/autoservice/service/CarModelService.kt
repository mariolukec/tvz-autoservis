package tvz.lukec.autoservice.service

import tvz.lukec.autoservice.rest.domain.dto.CarModelDto

interface CarModelService {
    fun getAllModelsByMake(makeId: Long): List<CarModelDto>
}
