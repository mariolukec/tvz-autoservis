package tvz.lukec.autoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import tvz.lukec.autoservice.rest.config.security.OAuth2ResourceServerConfig

@SpringBootApplication(
        exclude = [
            OAuth2ResourceServerConfig::class,
            AuthorizationServerConfigurerAdapter::class
        ]
)
class AutoserviceApplication

fun main(args: Array<String>) {
    runApplication<AutoserviceApplication>(*args)
}
