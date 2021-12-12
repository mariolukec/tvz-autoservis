package tvz.lukec.autoservice.mapper.dto

import org.mapstruct.Mapper
import tvz.lukec.autoservice.model.Service
import tvz.lukec.autoservice.rest.domain.dto.ServiceDto

@Mapper(componentModel = "spring", uses = [CarMapper::class, RoleMapper::class])
interface ServiceMapper {
    fun toDto(service: Service): ServiceDto
    fun toDtos(services: List<Service>): List<ServiceDto>
}