package com.realworld.webflux_coroutine.security.authentication

import com.realworld.webflux_coroutine.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomReactiveUserDetailsService (
    private val userRepository: UserRepository
): ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> = mono {
        val user: com.realworld.webflux_coroutine.domain.User = userRepository.findByEmail(username).awaitSingle() ?: throw BadCredentialsException("Invalid Credentials")
        return@mono User(user.email, user.password, listOf(user))
    }
}