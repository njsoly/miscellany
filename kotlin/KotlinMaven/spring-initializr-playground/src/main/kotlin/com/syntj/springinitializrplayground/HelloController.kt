package com.syntj.springinitializrplayground

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @RequestMapping("/")
    fun index(): String {
        println(javaClass.name + " hit at request mapping /.")
        return "Greetings from Spring Boot!\n"
    }
}
