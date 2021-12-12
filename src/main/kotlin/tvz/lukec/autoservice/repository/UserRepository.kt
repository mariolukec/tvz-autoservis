package tvz.lukec.autoservice.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.Role
import tvz.lukec.autoservice.model.User


@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?
    override fun existsById(id: Long): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByIdAndRoleName(id: Long, roleName: String): Boolean

    @Query(value = "select count(*) from car where user_id = :userId", nativeQuery = true)
    fun getCarCount(@Param("userId") userId: Long): Long

    @Query(value = "select distinct count(service.id) from service" +
            " inner join car on service.car_id = car.id " +
            " where car.user_id = :userId", nativeQuery = true)
    fun getServiceCount(@Param("userId") userId: Long): Int

    @Query(value = "select count(sm.*) from service_mechanic sm where sm.mechanic_id = :userId", nativeQuery = true)
    fun getServicesWorkedOnCount(@Param("userId") userId: Long): Int

    @Query(value = "select count(distinct(s.car_id)) from service s " +
            " left join service_mechanic sm on sm.service_id = s.id" +
            " where sm.mechanic_id = :userId", nativeQuery = true)
    fun getCarsWorkedOnCount(@Param("userId") userId: Long): Long
    fun findAllByRole(role: Role): List<User>
    fun findAllClientsPageableByRole(role: Role, pageable: Pageable): Page<User>
}
