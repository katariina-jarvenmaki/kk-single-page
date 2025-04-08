package com.katariinatuulia.com.kk_single_page

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import com.katariinatuulia.com.kk_single_page.JwtTokens
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals

@SpringBootTest
class JwtTokensTest {

    @Autowired
    lateinit var jwt: JwtTokens 

    @Test
    fun testTokenGenerationAndValidation() {

        val token = jwt.generate()
        println("Generated Token: $token")
        assertNotNull(token, "Token should not be null")

        val isValid = jwt.validate(token)
        println("Is token valid? $isValid")
        assertTrue(isValid, "Token should be valid")

        val username = jwt.username(token)
        println("Username from token: $username")
    }
}