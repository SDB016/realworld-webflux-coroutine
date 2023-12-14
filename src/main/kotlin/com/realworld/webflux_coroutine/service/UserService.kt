package com.realworld.webflux_coroutine.service

import com.realworld.webflux_coroutine.domain.User
import com.realworld.webflux_coroutine.dto.UserRegistrationRequest
import com.realworld.webflux_coroutine.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun registration(content: UserRegistrationRequest) {
        User(
            email = content.email,
            password = content.password,
            name = content.username
        )
    }

}
