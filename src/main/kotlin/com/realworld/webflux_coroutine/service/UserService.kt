package com.realworld.webflux_coroutine.service

import com.realworld.webflux_coroutine.domain.User
import com.realworld.webflux_coroutine.dto.UserRegistrationRequest
import com.realworld.webflux_coroutine.dto.response.UserResponse
import com.realworld.webflux_coroutine.exception.EmailDuplicatedException
import com.realworld.webflux_coroutine.repository.UserRepository
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtManager: JwtManager
) {
    suspend fun registration(content: UserRegistrationRequest): UserResponse = coroutineScope {
        if(validDuplicatedEmail(content.email)) {
            throw EmailDuplicatedException("Duplicated Email")
        }
        val token = async { jwtManager.generateToken(content.email) }
        val user = userRepository.save(
            User(
                email = content.email,
                password = content.password,
                name = content.username,
                token = token.await()
            )
        ).awaitSingle()

        user.toResponse(user)
    }

    suspend fun validDuplicatedEmail(email: String): Boolean =
        userRepository.existsByEmail(email).awaitSingle()

}
