package com.realworld.webflux_coroutine.dto.response

data class UserResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String,
    val image: String
)
