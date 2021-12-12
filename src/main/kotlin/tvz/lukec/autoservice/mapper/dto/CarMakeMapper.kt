package tvz.lukec.autoservice.mapper.dto

import org.mapstruct.Mapper
import tvz.lukec.autoservice.model.CarMake
import tvz.lukec.autoservice.rest.domain.dto.CarMakeDto

@Mapper(componentModel = "spring")
interface CarMakeMapper {
    fun toDto(make: CarMake): CarMakeDto
    fun toDtos(carMakes: List<CarMake>): List<CarMakeDto>
}