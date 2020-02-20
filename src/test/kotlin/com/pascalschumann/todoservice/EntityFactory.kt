package com.pascalschumann.todoservice

import com.pascalschumann.todoservice.api.model.Todo
import org.apache.commons.lang3.RandomStringUtils

val randomLength = 10
var idCounter = 1000L

/**
 * Provides static methods to generate entities.
 */
object EntityFactory {
    fun createTodo(): Todo {
        return Todo(createId(), createName())
    }

    fun createId(): Long {
        return idCounter++
    }

    fun createName(): String {
        return RandomStringUtils.randomAlphabetic(randomLength)
    }
}