package tvz.lukec.autoservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tvz.lukec.autoservice.model.Role

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun getRoleByName(name: String?): Role?
}
