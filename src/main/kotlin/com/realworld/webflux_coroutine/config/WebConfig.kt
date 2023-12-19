package com.realworld.webflux_coroutine.config

import com.realworld.webflux_coroutine.security.authentication.CustomReactiveUserDetailsService
import com.realworld.webflux_coroutine.service.JwtManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFluxSecurity
class WebConfig: WebFluxConfigurer {

    companion object {
        val PERMITTED_URLS = arrayOf("/")
    }

    @Bean
    fun webFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationFilter: AuthenticationWebFilter,
        jwtManager: JwtManager
    ) = http
        .csrf { it.disable() }
        .logout { it.disable() }
        .authorizeExchange {
            it.pathMatchers(*PERMITTED_URLS).permitAll()
            it.anyExchange().authenticated()
        }
        .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationWebFilter(
        reactiveAuthenticationManager: ReactiveAuthenticationManager,
        jwtConverter: ServerAuthenticationConverter,
        serverAuthenticationSuccessHandler: ServerAuthenticationSuccessHandler,
        serverAuthenticationFailureHandler: ServerAuthenticationFailureHandler
    ): AuthenticationWebFilter = AuthenticationWebFilter(reactiveAuthenticationManager).also {
        it.setRequiresAuthenticationMatcher { ServerWebExchangeMatchers.pathMatchers(HttpMethod.POST, "/login").matches(it) }
        it.setServerAuthenticationConverter(jwtConverter)
        it.setAuthenticationSuccessHandler(serverAuthenticationSuccessHandler)
        it.setAuthenticationFailureHandler(serverAuthenticationFailureHandler)
        it.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance())
    }

    @Bean
    fun reactiveAuthenticationManager(
        reactiveUserDetailsService: CustomReactiveUserDetailsService,
        passwordEncoder: PasswordEncoder
    ): ReactiveAuthenticationManager = UserDetailsRepositoryReactiveAuthenticationManager(
        reactiveUserDetailsService
    ).also {
        it.setPasswordEncoder(passwordEncoder)
    }

    @Bean
    fun jacksonDecoder(): AbstractJackson2Decoder = Jackson2JsonDecoder()
}