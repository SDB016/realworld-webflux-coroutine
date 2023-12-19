package com.realworld.webflux_coroutine.exception

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(EmailDuplicatedException::class)
    @ResponseStatus()
    fun emailDuplicatedExceptionHandler(e: EmailDuplicatedException): ExceptionResponse {
        e.subject
    }
}

data class ExceptionResponse(val errors: Map<String, List<String>>)