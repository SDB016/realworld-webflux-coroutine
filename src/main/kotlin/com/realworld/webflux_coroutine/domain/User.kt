package com.realworld.webflux_coroutine.domain

class User(
    private val email: String,
    private val password: String,
    private val name: String,
    private val id: Long? = null
) {
}