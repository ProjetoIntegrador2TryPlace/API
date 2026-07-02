package com.tryplace.tryplace

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
class TryplaceApplication

fun main(args: Array<String>) {
    runApplication<TryplaceApplication>(*args)
}