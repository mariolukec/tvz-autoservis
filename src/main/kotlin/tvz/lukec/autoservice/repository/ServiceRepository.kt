package tvz.lukec.autoservice.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.Service
import com.querydsl.core.types.Predicate

@Repository
interface ServiceRepository : JpaRepository<Service, Long>, QuerydslPredicateExecutor<Service> {
    fun getAllByCarId(carId: Long): List<Service>

    fun getAllByCarUserId(userId: Long): List<Service>

    @Query(value = "select coalesce(sum(service.price), 0) from service where EXTRACT(MONTH from service.date_received) = :monthId " +
            "                                    and EXTRACT( YEAR from service.date_received) = :yearId " +
            "                                    and service.car_id = :carId", nativeQuery = true)
    fun getCostForMonth(@Param("monthId") monthId: Int, @Param("yearId") year: Long, @Param("carId") carId: Long): Double

    fun getAllByCarUserEmailOrderByDateFinishedDesc(email: String): List<Service>

    override fun findAll(predicate: Predicate, pageable: Pageable): Page<Service>

    fun findAllByPickedUpAtIsNull(): List<Service?>?
}
