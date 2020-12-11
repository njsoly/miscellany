package com.syntj.springinitializrplayground

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Table1Controller (val table1Repository: Table1Repository) {
    @RequestMapping("/table1")
    fun index(): String {
        println(javaClass.name + " hit at request mapping /table1.")
        return "first entry in table1 is ${table1Repository.findById(1L).get().name}.\n"
    }
}
