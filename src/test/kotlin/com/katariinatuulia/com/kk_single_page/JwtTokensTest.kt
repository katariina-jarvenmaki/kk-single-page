package com.katariinatuulia.com.kk_single_page

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.katariinatuulia.com.kk_single_page.JwtTokens

@SpringBootTest
class JwtTokensTest {

    @tes
    fun main() {
        val jwt = JwtTokens()
        val token = jwt.generate()
        println("Generated Token: $token")
        
        val isValid = jwt.validate(token)
        println("Is token valid? $isValid")
    
        val user = jwt.username(token)
        println("Username from token: $user")
    }
    
}
