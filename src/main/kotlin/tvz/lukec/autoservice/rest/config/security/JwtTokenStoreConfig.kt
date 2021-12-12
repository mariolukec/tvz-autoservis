package tvz.lukec.autoservice.rest.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.util.*


@Configuration
class JwtTokenStoreConfig {
    @Value("\${app.jwt.sign-key}")
    private val jwtSignKey: String? = null
    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setSigningKey(jwtSignKey)
        return jwtAccessTokenConverter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun tokenEnhancerChain(): TokenEnhancerChain {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()) as List<TokenEnhancer>?)
        return tokenEnhancerChain
    }
}
