package com.katariinatuulia.com.kk_single_page

import org.springframework.stereotype.Component

// JWT TOKENS

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*

@Component
class JwtTokens {

    private val signer = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256)

    fun generate(): String {

        val now = System.currentTimeMillis()
        val expiry = now + 1000 * 60 * 60 // 1 hour

        return Jwts.builder()
            .setSubject("test")
            .setIssuedAt(Date(now))
            .setExpiration(Date(expiry))
            .signWith(signer)
            .compact()
    }
}

