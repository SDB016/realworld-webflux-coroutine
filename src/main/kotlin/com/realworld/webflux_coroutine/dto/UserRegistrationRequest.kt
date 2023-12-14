package com.realworld.webflux_coroutine.dto

data class UserRegistrationRequest(
    val email: String,
    val password: String,
    val username: String
)
