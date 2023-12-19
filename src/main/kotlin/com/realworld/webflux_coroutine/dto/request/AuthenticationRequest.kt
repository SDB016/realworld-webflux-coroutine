package com.realworld.webflux_coroutine.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)

data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val username: String
)
