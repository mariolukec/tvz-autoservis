package tvz.lukec.autoservice.service.impl

import org.springframework.stereotype.Service
import tvz.lukec.autoservice.mapper.dto.CarModelMapper
import tvz.lukec.autoservice.model.CarModel
import tvz.lukec.autoservice.repository.CarModelRepository
import tvz.lukec.autoservice.rest.domain.dto.CarModelDto
import tvz.lukec.autoservice.service.CarModelService

@Service
class CarModelServiceImpl(
        private val carModelRepository: CarModelRepository,
        private val carModelMapper: CarModelMapper
) : CarModelService {

    override fun getAllModelsByMake(makeId: Long): List<CarModelDto> {
        val carModels: List<CarModel> = carModelRepository.getCarModelsByMakeId(makeId)
        return carModelMapper.toDtos(carModels)
    }
}