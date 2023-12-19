package com.realworld.webflux_coroutine.security.authentication

import com.realworld.webflux_coroutine.dto.request.LoginRequest
import com.realworld.webflux_coroutine.exception.HttpExceptionFactory.badRequest
import jakarta.validation.Validator
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.core.ResolvableType
import org.springframework.http.MediaType
import org.springframework.http.codec.json.AbstractJackson2Decoder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JWTConverter(
    private val jackson2Decoder: AbstractJackson2Decoder,
    private val validator: Validator
): ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> = mono {
        val loginRequest = getUsernameAndPassword(exchange!!) ?: throw badRequest()

        if (validator.validate(loginRequest).isNotEmpty()) {
            throw badRequest()
        }
        return@mono UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
    }

    private suspend fun getUsernameAndPassword(exchange: ServerWebExchange): LoginRequest? {
        val dataBuffer = exchange.request.body
        val type = ResolvableType.forClass(LoginRequest::class.java)
        return jackson2Decoder
            .decodeToMono(dataBuffer, type, MediaType.APPLICATION_JSON, mapOf())
            .onErrorResume { Mono.empty<LoginRequest>() }
            .cast(LoginRequest::class.java)
            .awaitFirstOrNull()
    }
}