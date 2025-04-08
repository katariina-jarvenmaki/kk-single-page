package com.katariinatuulia.com.kk_single_page

import org.springframework.stereotype.Component
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Claims
import java.util.*

@Component
class JwtTokens {

    private val signer = Keys.secretKeyFor(SignatureAlgorithm.HS256)

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

    fun validate(token: String): Boolean {
        return try {
            getClaims(token).expiration.after(Date())
        } 
        catch (e: Exception) {
            false
        }
    }

    fun username(token: String): String = getClaims(token).subject

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder().setSigningKey(signer).build().parseClaimsJws(token).body
}
