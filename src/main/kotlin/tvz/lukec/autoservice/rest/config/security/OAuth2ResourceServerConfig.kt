package tvz.lukec.autoservice.rest.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
class OAuth2ResourceServerConfig @Autowired constructor(private val tokenStore: TokenStore) : ResourceServerConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("context-path")
                .tokenStore(tokenStore)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/oauth/**", "/users/new/**").permitAll()
    }
}
