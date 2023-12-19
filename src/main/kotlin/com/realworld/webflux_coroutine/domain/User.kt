package com.realworld.webflux_coroutine.domain

import com.realworld.webflux_coroutine.dto.response.UserResponse
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class User(
    private val email: String,
    private val password: String,
    private val name: String,
    private val bio: String? = null,
    private val image: String? = null,
    private val token: String,
    @Id private val id: Long? = null
) {

    fun toResponse(user: User): UserResponse = UserResponse(
        email = user.email,
        token = token,
        username = user.name,
        bio = user.bio ?: "",
        image = user.image ?: ""
    )
}