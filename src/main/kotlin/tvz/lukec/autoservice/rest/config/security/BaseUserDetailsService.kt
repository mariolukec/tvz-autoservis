package tvz.lukec.autoservice.rest.config.security

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tvz.lukec.autoservice.model.User
import tvz.lukec.autoservice.repository.UserRepository

@Primary
@Service
class BaseUserDetailsService(
        private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepository.findByEmail(username)
        if (user == null) {
            LOG.info("User not found with email={}", username)
            throw UsernameNotFoundException(username)
        }
        return org.springframework.security.core.userdetails.User(
                username, user.password, true, true, true, true, listOf(user.role))
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(BaseUserDetailsService::class.java)
    }
}
