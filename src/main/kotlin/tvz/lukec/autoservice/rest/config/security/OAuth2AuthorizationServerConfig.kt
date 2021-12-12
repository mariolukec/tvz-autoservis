package tvz.lukec.autoservice.rest.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationServerConfig @Autowired constructor(@param:Qualifier("authenticationManagerBean") private val authenticationManager: AuthenticationManager, private val tokenStore: TokenStore, private val tokenEnhancerChain: TokenEnhancerChain, private val passwordEncoder: PasswordEncoder, private val userDetailsService: UserDetailsService, private val dataSource: DataSource) : AuthorizationServerConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(NoOpPasswordEncoder.getInstance())
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .tokenEnhancer(tokenEnhancerChain)
    }

    @Autowired
    @Throws(Exception::class)
    fun init(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore)
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }
}
