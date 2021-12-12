package tvz.lukec.autoservice.mapper.dto

import org.mapstruct.Mapper
import org.springframework.stereotype.Component
import tvz.lukec.autoservice.model.CarModel
import tvz.lukec.autoservice.model.Role
import tvz.lukec.autoservice.rest.domain.dto.CarModelDto

@Mapper(componentModel = "spring", uses = [CarMakeMapper::class, RoleMapper::class])
interface CarModelMapper {
    fun toDto(model: CarModel): CarModelDto
    fun toDtos(carModels: List<CarModel>): List<CarModelDto>
}

@Component
class RoleMapper {
    fun map(role: Role): String {
        return role.name!!
    }
}
