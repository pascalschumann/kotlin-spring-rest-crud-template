package com.pascalschumann.todoservice.api.repository

import com.pascalschumann.todoservice.api.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo?, Long?>