package tvz.lukec.autoservice.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.rest.domain.dto.UserDto
import tvz.lukec.autoservice.rest.domain.form.EditUserForm
import tvz.lukec.autoservice.rest.domain.form.UpdatePasswordForm
import tvz.lukec.autoservice.rest.domain.form.UserForm

interface UserService {
    fun clientExistsById(userId: Long): Boolean
    fun getDtoByEmail(email: String): UserDto
    fun save(userForm: UserForm): UserDto
    fun update(editUserForm: EditUserForm): UserDto
    fun updatePassword(updatePasswordForm: UpdatePasswordForm)
    fun getAllClients(): List<UserDto>
    fun getAllClientsPageable(pageable: Pageable): Page<UserDto>
    fun getAllMechanics(): List<UserDto>
}