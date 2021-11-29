package io.nikolamicic21.simpletodoapp.broadcaster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleTodoAppBroadcasterApplication

fun main(args: Array<String>) {
	runApplication<SimpleTodoAppBroadcasterApplication>(*args)
}
