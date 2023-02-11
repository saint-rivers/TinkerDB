package com.saintrivers.tinkerdb

import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TinkerDbApplication

fun main(args: Array<String>): Unit = runBlocking {
    runApplication<TinkerDbApplication>(*args)
}
