package com.realworld.webflux_coroutine.repository

import com.realworld.webflux_coroutine.domain.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveCrudRepository<User, Long> {

    fun existsByEmail(email: String): Mono<Boolean>


}
