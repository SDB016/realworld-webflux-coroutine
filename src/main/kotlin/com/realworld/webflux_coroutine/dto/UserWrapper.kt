package com.realworld.webflux_coroutine.dto

import com.fasterxml.jackson.annotation.JsonProperty

class UserWrapper<T>(@JsonProperty("user") val content: T)

fun <T> T.toUserWrapper(): UserWrapper<T> = UserWrapper(this)