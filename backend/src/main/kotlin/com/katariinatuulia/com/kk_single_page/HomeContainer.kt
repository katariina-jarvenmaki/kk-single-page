package com.katariinatuulia.com.kk_single_page

//******************** IMPORTS ********************//

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//******************** APP ********************//

@RestController
@RequestMapping("/")
class HomeController {

    @GetMapping
    fun home(): String {
        return "Loading the home-page..."
    }
    
}