package com.katariinatuulia.com.kk_single_page

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HomeController {

    @GetMapping
    fun home(): String {
        return "Loading login form..."
    }
    
}