package tvz.lukec.autoservice.mapper.dto

import org.springframework.stereotype.Service
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.UserRepository
import tvz.lukec.autoservice.rest.domain.dto.UserDto

@Service
class UserMapper(
        private val userRepository: UserRepository
) {
    fun toDto(user: User): UserDto {
        return UserDto().also {
            it.id = user.id
            it.email = user.email
            it.image = user.image
            it.role = user.role?.name
            it.firstName = user.firstName
            it.lastName = user.lastName
            it.carCount = if (user.isMechanic()) userRepository.getCarsWorkedOnCount(user.id!!) else userRepository.getCarCount(user.id!!)
            it.serviceCount = if (user.isMechanic()) userRepository.getServicesWorkedOnCount(user.id!!) else userRepository.getServiceCount(user.id!!)
        }
    }

    fun toDtos(users: List<User>): List<UserDto> {
        return users.map { toDto(it) }
    }
}