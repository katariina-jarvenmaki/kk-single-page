package com.katariinatuulia.com.kk_single_page

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HealthController {

    @GetMapping("/healthcheck")
    fun healthCheck(): Map<String, String> {
        return mapOf("status" to "OK")
    }
}
