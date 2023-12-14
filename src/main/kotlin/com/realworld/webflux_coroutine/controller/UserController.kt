package com.realworld.webflux_coroutine.controller

import com.realworld.webflux_coroutine.dto.UserRegistrationRequest
import com.realworld.webflux_coroutine.dto.UserWrapper
import com.realworld.webflux_coroutine.dto.request.UserAuthRequest
import com.realworld.webflux_coroutine.dto.response.UserResponse
import com.realworld.webflux_coroutine.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    suspend fun registration(@RequestBody request: UserWrapper<UserRegistrationRequest>): UserWrapper<UserResponse> {
        userService.registration(request.content)
    }

//    @PostMapping("/login")
//    suspend fun login(@RequestBody request: UserWrapper<UserAuthRequest>): UserWrapper<UserResponse> {
//        userService.authentication(request.content)
//    }
}