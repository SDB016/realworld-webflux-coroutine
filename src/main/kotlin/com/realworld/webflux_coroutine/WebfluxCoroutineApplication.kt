package com.realworld.webflux_coroutine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.web.reactive.config.EnableWebFlux

@EnableR2dbcRepositories
@EnableWebFlux
@SpringBootApplication
class WebfluxCoroutineApplication

fun main(args: Array<String>) {
    runApplication<WebfluxCoroutineApplication>(*args)
}
