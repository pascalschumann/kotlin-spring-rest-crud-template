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
    private val repository: TodoRepository = repository

    @GetMapping(Constants.API_ENDPOINT_TODOS)
    fun getAll(): MutableList<Todo?> {
        return repository.findAll()
    }

    @PostMapping(Constants.API_ENDPOINT_TODOS)
    fun createTodo(@RequestBody newTodo: Todo): Todo {
        return repository.save(newTodo)
    }

    // Single item
    @GetMapping(Constants.API_ENDPOINT_TODOS + "/{id}")
    fun getOne(@PathVariable id: Long, response: HttpServletResponse): Todo? {
        val foundTodo: Optional<Todo?> = repository.findById(id)
        return if (foundTodo.isPresent()) {
            foundTodo.get()
        } else {
            response.status = HttpStatus.NOT_FOUND.value()
            return null
        }
    }

    @PutMapping(Constants.API_ENDPOINT_TODOS + "/{id}")
    fun putTodoAsReplace(@PathVariable id: Long, @RequestBody givenTodo: Todo, response: HttpServletResponse): Todo? {
        val foundTodo: Optional<Todo?> = repository.findById(id)
        if (foundTodo.isPresent()) {
            val updatedTodo = Todo(id, givenTodo.name)
            return repository.save(updatedTodo)
        } else {
            response.status = HttpStatus.NOT_FOUND.value()
            return null
        }
    }

    @DeleteMapping(Constants.API_ENDPOINT_TODOS + "/{id}")
    fun deleteTodo(@PathVariable id: Long) {
        repository.deleteById(id)
    }
}
