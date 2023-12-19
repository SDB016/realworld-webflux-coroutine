package com.realworld.webflux_coroutine.security.authentication

import com.realworld.webflux_coroutine.exception.HttpExceptionFactory.unauthorized
import com.realworld.webflux_coroutine.service.JwtManager
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JWTServerAuthenticationFailureHandler: ServerAuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        webFilterExchange: WebFilterExchange?,
        exception: AuthenticationException?
    ): Mono<Void> = mono {
        val exchange = webFilterExchange?.exchange ?: throw unauthorized()
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        exchange.response.setComplete().awaitFirstOrNull()
    }
}

@Component
class JWTServerAuthenticationSuccessHandler(
    private val jwtManager: JwtManager
): ServerAuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange?,
        authentication: Authentication?
    ): Mono<Void> = mono {
        val principal = authentication?.principal ?: throw unauthorized()

        when (principal) {
            is User -> {
                val roles = principal.authorities.map { it.authority }.toTypedArray()
                val token = jwtManager.generateToken(principal.username)
                webFilterExchange?.exchange?.response?.headers?.set("Authorization", token)
            }
        }

        return@mono null
    }

}