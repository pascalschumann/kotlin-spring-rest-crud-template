package com.pascalschumann.todoservice.api.configuration

import com.pascalschumann.todoservice.api.model.Todo
import com.pascalschumann.todoservice.api.repository.TodoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


/**
 * Initializes the spring dev profile
 */
@Component
@Profile(Constants.SPRING_PROFILE_DEV)
class DevProfileInitializer : ApplicationRunner {
    private val log: Logger = LoggerFactory.getLogger(DevProfileInitializer::class.java)
    @Throws(Exception::class)
    override fun run(
            args: ApplicationArguments) { // fill, if something has to be done after spring start
    }

    @Bean
    fun initDatabase(repository: TodoRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            log.info("Prefilling database.");
            for (i in 1..10) {
                repository.save(Todo(null, "todo ${i}"))
            }
        }
    }
}
