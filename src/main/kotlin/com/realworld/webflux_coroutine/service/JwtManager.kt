package com.realworld.webflux_coroutine.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtManager(
    @Value("\${jwt.key}")
    private val key: ByteArray
) {

    private val jwtKey = Keys.hmacShaKeyFor(key)
    private val parser = Jwts.parser().verifyWith(jwtKey).build()
    private val EXPIRATION_MILLIS = 1000 * 60 * 15

    suspend fun generateToken(username: String): String {


        return Jwts.builder()
            .subject(username)
            .signWith(jwtKey)
            .expiration(Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
            .issuer("DB")
            .issuedAt(Date.from(Instant.now()))
            .compact()
    }

    fun isValid(token: String, userDetails: UserDetails?): Boolean {
        val claims = parser.parseSignedClaims(token).payload
    }
}