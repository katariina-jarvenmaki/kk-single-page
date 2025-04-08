package com.katariinatuulia.com.kk_single_page

//******************** IMPORTS ********************//

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*

//******************** JWT TOKENS ********************//

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

//******************** JWT FILTER ********************//

@Component
class JwtFilter(private val jwtTokens: JwtTokens) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            if (jwtTokens.validate(token)) {
                val username = jwtTokens.username(token)
                val auth = UsernamePasswordAuthenticationToken(username, null, emptyList())
                auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = auth
            }
        }

        filterChain.doFilter(request, response)
    }
}
