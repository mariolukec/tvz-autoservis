package tvz.lukec.autoservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.CarModel

@Repository
interface CarModelRepository : JpaRepository<CarModel, Long> {
    fun getCarModelsByMakeId(makeId: Long): List<CarModel>
}