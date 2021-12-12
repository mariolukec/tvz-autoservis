package tvz.lukec.autoservice.service.impl

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tvz.lukec.autoservice.mapper.dto.UserMapper
import tvz.lukec.autoservice.mapper.form.UserFormMapper
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.RoleRepository
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.rest.domain.dto.UserDto
import tvz.lukec.autoservice.rest.domain.form.EditUserForm
import tvz.lukec.autoservice.rest.domain.form.UpdatePasswordForm
import tvz.lukec.autoservice.rest.domain.form.UserForm
import tvz.lukec.autoservice.service.UserService
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val userFormMapper: UserFormMapper,
        private val userMapper: UserMapper,
        private val roleRepository: RoleRepository
) : UserService {
    val ROLE_CLIENT = "ROLE_CLIENT"
    val ROLE_MECHANIC = "ROLE_MECHANIC"

    override fun clientExistsById(userId: Long): Boolean {
        return userRepository.existsByIdAndRoleName(userId, ROLE_CLIENT)
    }

    override fun getDtoByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email) ?: throw EntityNotFoundException()
        return userMapper.toDto(user)
    }

    override fun save(userForm: UserForm): UserDto {
        val user = userFormMapper.mapToEntity(userForm)
        return userMapper.toDto(userRepository.save(user))
    }

    override fun update(editUserForm: EditUserForm): UserDto {
        val user = userRepository.getById(editUserForm.id!!)
        user.also {
            it.firstName = editUserForm.firstName
            it.lastName = editUserForm.lastName
            it.email = editUserForm.email
            it.role = roleRepository.getRoleByName(editUserForm.role)
        }

        val saved = userRepository.save(user)
        return userMapper.toDto(saved)
    }

    override fun updatePassword(updatePasswordForm: UpdatePasswordForm) {
        val user = userRepository.getById(updatePasswordForm.id!!)
        if(passwordEncoder.matches(updatePasswordForm.oldPassword, user.password)){
            user.password = passwordEncoder.encode(updatePasswordForm.newPassword)
        } else {
            throw Exception("Old password does not match!")
        }
    }

    override fun getAllClients(): List<UserDto> {
        val role = roleRepository.getRoleByName(ROLE_CLIENT)
        val users = userRepository.findAllByRole(role!!)
        return userMapper.toDtos(users)
    }

    override fun getAllClientsPageable(pageable: Pageable): Page<UserDto> {
        val role = roleRepository.getRoleByName(ROLE_CLIENT)
        val users: Page<User> = userRepository.findAllClientsPageableByRole(role!!, pageable)
        return users.map { userMapper.toDto(it) }
    }

    override fun getAllMechanics(): List<UserDto> {
        val role = roleRepository.getRoleByName(ROLE_MECHANIC)
        val users = userRepository.findAllByRole(role!!)
        return userMapper.toDtos(users)
    }
}