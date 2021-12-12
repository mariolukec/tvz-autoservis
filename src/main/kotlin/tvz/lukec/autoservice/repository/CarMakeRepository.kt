package tvz.lukec.autoservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.CarMake

@Repository
interface CarMakeRepository : JpaRepository<CarMake, Long>
