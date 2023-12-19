package com.realworld.webflux_coroutine.exception

class EmailDuplicatedException(val subject: String, message: String) : RuntimeException() {

}
