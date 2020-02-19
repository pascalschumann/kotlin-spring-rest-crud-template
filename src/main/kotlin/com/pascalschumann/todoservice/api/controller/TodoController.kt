package com.pascalschumann.todoservice.api.controller

import com.pascalschumann.todoservice.api.configuration.Constants
import com.pascalschumann.todoservice.api.model.Todo
import com.pascalschumann.todoservice.api.repository.TodoRepository
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse


/**
 * CRUD endpoint
 */
@RestController
@Api(value = "Todos", description = "is an example CRUD endpoint.", tags = ["Todos"])
class TodoController(repository: TodoRepository) {
    private val repository: TodoRepository
    @GetMapping(Constants.API_ENDPOINT_TODOS)
    fun all(): MutableList<Todo?> {
        return repository.findAll()
    }

    @PostMapping(Constants.API_ENDPOINT_TODOS)
    fun createTodo(@RequestBody newTodo: Todo): Todo {
        return repository.save(newTodo)
    }

    // Single item
    @GetMapping(Constants.API_ENDPOINT_TODOS.toString() + "/{id}")
    fun one(@PathVariable id: Long, response: HttpServletResponse): Todo? {
        val foundTodo: Optional<Todo?> = repository.findById(id)
        return if (foundTodo.isPresent()) {
            foundTodo.get()
        } else {
            response.status = HttpStatus.NOT_FOUND.value()
            null
        }
    }

    @PutMapping(Constants.API_ENDPOINT_TODOS)
    fun replaceTodo(@RequestBody newTodo: Todo): Todo {
        repository.findById(newTodo.id)
                .orElseThrow({ RuntimeException("Could not found given entity.") })
        return repository.save(newTodo)
    }

    @DeleteMapping(Constants.API_ENDPOINT_TODOS.toString() + "/{id}")
    fun deleteTodo(@PathVariable id: Long) {
        repository.deleteById(id)
    }

    init {
        this.repository = repository
    }
}
