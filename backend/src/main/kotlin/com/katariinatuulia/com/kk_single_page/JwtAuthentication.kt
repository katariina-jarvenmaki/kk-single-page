package com.katariinatuulia.com.kk_single_page

//******************** IMPORTS ********************//

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.http.ResponseEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*

//******************** JWT TOKENS ********************//

@Component
class JwtTokens {

    private val signer = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    
    fun generate(username: String): String {
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

//******************** SECURITY CONFIG ********************//

@Configuration
class SecurityConfig(private val jwtFilter: JwtFilter) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { it.disable() } // No CSRF for APIs
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // JWT means stateless
            .authorizeHttpRequests {
                it
                .requestMatchers(
                    "/", 
                    "/index.html", 
                    "/static/**", 
                    "/*.html", 
                    "/api/public/**", 
                    "/api/healthcheck", 
                    "/api/auth/login"
                ).permitAll()
                .requestMatchers("/api/data/**").authenticated() // Secured endpoints
                .anyRequest().authenticated() // All else needs token
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java) // Use your custom filter

        return http.build()
    }
}

//******************** ENDPOINTS ********************//

@RestController
@RequestMapping("/api/public")
class AuthController(private val jwtTokens: JwtTokens) {

    @GetMapping("/token")
    fun getToken(): Map<String, String> {
        val token = jwtTokens.generate("testuser") // or any default/test username
        return mapOf("token" to token)
    }

}

@RestController
@RequestMapping("/api/secure")
class SecureController {

    @GetMapping("/data")
    fun securedData(): Map<String, String> {
        val user = SecurityContextHolder.getContext().authentication.name
        return mapOf("message" to "Hello, $user! You have access.")
    }
}

//******************** LOGIN ********************//

@RestController
@RequestMapping("/api/auth")
class AuthLoginController(
    private val jwtTokens: JwtTokens,
    private val userRepository: UserRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {

        println("Login attempt: ${authRequest.username}")
        val user = userRepository.findByUsername(authRequest.username)

        if (user != null && user.password == authRequest.password) {
            val token = jwtTokens.generate(user.username)
            return ResponseEntity.ok(mapOf("token" to token, "username" to user.username))
        } else {
            return ResponseEntity.status(401).body("Invalid credentials")
        }
    }    
}


//******************** CORS ********************//

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("*")
            }
        }
    }
}

//******************** DATA CLASSES ********************//

data class AuthRequest(
    val username: String,
    val password: String
)

