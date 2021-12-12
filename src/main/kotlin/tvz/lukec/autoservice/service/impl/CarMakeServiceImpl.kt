package tvz.lukec.autoservice.service.impl

import org.springframework.stereotype.Service
import tvz.lukec.autoservice.mapper.dto.CarMakeMapper
import tvz.lukec.autoservice.model.CarMake
import tvz.lukec.autoservice.repository.CarMakeRepository
import tvz.lukec.autoservice.rest.domain.dto.CarMakeDto
import tvz.lukec.autoservice.service.CarMakeService

@Service
class CarMakeServiceImpl(
        private val carMakeRepository: CarMakeRepository,
        private val carMakeMapper: CarMakeMapper
) : CarMakeService {

    override val allMakes: List<CarMakeDto>
        get() {
            val carMakes: List<CarMake> = carMakeRepository.findAll()
            return carMakeMapper.toDtos(carMakes)
        }
}
