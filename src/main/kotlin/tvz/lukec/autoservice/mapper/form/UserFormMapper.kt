package tvz.lukec.autoservice.mapper.form

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.RoleRepository
import tvz.lukec.autoservice.rest.domain.form.UserForm

@Component
class UserFormMapper(
        private val roleRepository: RoleRepository,
        private val passwordEncoder: PasswordEncoder
) {

    fun mapToEntity(userForm: UserForm): User {
        val user = User()
        user.firstName = userForm.firstName
        user.lastName = userForm.lastName
        user.password = passwordEncoder.encode(userForm.password)
        user.email = userForm.email
        user.role = roleRepository.getRoleByName(userForm.role)

        return user
    }
}