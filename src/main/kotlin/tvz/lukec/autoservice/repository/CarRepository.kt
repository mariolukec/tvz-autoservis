package tvz.lukec.autoservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.Car

@Repository
interface CarRepository : JpaRepository<Car, Long> {
    fun getAllByUserEmail(userEmail: String): List<Car>

    override fun existsById(id: Long): Boolean

    @Query("select count(s.id) from Service s where s.car.id = :carId")
    fun getServiceCount(@Param("carId") carId: Long): Long

    @Query("select COALESCE(sum(s.price), 0) from Service s where s.car.id = :carId")
    fun getServiceTotalSum(@Param("carId") carId: Long): Double

    @Query(value = "select s.mileage from Service s left join Car c on c.id = s.car_id where c.id = :carId and s.date_finished is not null order by s.date_finished desc limit 1", nativeQuery = true)
    fun getLastServiceAt(@Param("carId") carId: Long): Int?

    fun getAllByUserId(userId: Long): List<Car>
}
