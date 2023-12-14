package com.realworld.webflux_coroutine.dto.request

data class UserAuthRequest(
    val email: String,
    val password: String
)