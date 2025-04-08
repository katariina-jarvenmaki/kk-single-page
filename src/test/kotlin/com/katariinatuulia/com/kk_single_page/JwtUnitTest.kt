package com.katariinatuulia.com.kk_single_page

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.katariinatuulia.com.kk_single_page.JwtTokens

@SpringBootTest
class JwtUnitTest {

    @Autowired
    lateinit var jwtTokens: JwtTokens

    @Test
    fun testGenerateToken() {
        val token = jwtTokens.generate()
        println("Generated JWT: $token")
        assert(token.isNotEmpty())
    }
}
