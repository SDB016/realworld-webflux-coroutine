package com.realworld.webflux_coroutine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxCoroutineApplication

fun main(args: Array<String>) {
    runApplication<WebfluxCoroutineApplication>(*args)
}
