package com.disney

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class DisneyAssignment

//Run the main application
fun main(args: Array<String>) {
    runApplication<DisneyAssignment>(*args)
}
