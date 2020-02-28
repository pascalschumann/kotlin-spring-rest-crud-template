package com.pascalschumann.todoservice.integration.api

import com.pascalschumann.todoservice.EntityFactory
import com.pascalschumann.todoservice.api.configuration.Constants
import com.pascalschumann.todoservice.api.model.Todo
import com.pascalschumann.todoservice.integration.AbstractIT
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.apache.http.HttpStatus
import org.awaitility.Awaitility
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


/**
 * Tests the /todos endpoint
 */
class TodosIT : AbstractIT() {
    @AfterEach
    fun after() { // cleanup created entities
        val todos: Array<Todo> = readTodos()
        for (todo in todos) {
            deleteTodo(todo.id)
        }
    }

    /**
     * Includes testReadOne()
     */
    @Test
    fun testCreateTodo() {
        val expectedTodo: Todo = EntityFactory.createTodo()
        val createdTodo: Todo = createTodo(expectedTodo)
        Awaitility.await().until({ readTodo(createdTodo.id) != null })
        val actualTodo: Todo? = readTodo(createdTodo.id)
        Assertions.assertNotNull(actualTodo)
        Assertions.assertEquals(expectedTodo.name, actualTodo?.name)
    }

    @Test
    fun testReadAll() {
        val expectedTodo: Todo = EntityFactory.createTodo()
        val createdTodo: Todo = createTodo(expectedTodo)
        val actualTodos: Array<Todo> = readTodos()
        Assertions.assertArrayEquals(arrayOf<Todo>(createdTodo), actualTodos)
    }

    @Test
    fun testDeleteOne() {
        val expectedTodo: Todo = EntityFactory.createTodo()
        val createdTodo: Todo = createTodo(expectedTodo)
        deleteTodo(createdTodo.id)
        RestAssured.given().spec(REQUEST_SPECIFICATION).`when`()
                .get(Constants.API_ENDPOINT_TODOS.toString() + "/" + createdTodo.id).then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
    }

    @Test
    fun testReplaceOne() {
        val templateTodo: Todo = EntityFactory.createTodo()
        val createdTodo: Todo = createTodo(templateTodo)
        val expectedTodo: Todo = Todo(createdTodo.id, EntityFactory.createName())
        val replacedTodo: Todo = replaceTodo(expectedTodo)
        val actualTodo: Todo? = readTodo(expectedTodo.id)
        Assertions.assertEquals(expectedTodo.name, replacedTodo.name)
        Assertions.assertEquals(expectedTodo.name, actualTodo?.name)
    }

    // ------------------- utils methods --------------
    private fun readTodo(id: Long?): Todo? {
        return RestAssured.given().spec(REQUEST_SPECIFICATION).`when`()
                .get(Constants.API_ENDPOINT_TODOS.toString() + "/" + id).then()
                .statusCode(org.apache.http.HttpStatus.SC_OK).extract().`as`(Todo::class.java)
    }

    private fun deleteTodo(id: Long?) {
        RestAssured.given().spec(REQUEST_SPECIFICATION).`when`()
                .delete(Constants.API_ENDPOINT_TODOS.toString() + "/" + id).then()
                .statusCode(HttpStatus.SC_OK)
    }

    private fun readTodos(): Array<Todo> {
        return RestAssured.given().spec(REQUEST_SPECIFICATION).`when`()
                .get(Constants.API_ENDPOINT_TODOS).then().statusCode(HttpStatus.SC_OK)
                .extract().`as`(Array<Todo>::class.java)
    }

    private fun replaceTodo(todoToReplace: Todo): Todo {
        return RestAssured.given().spec(REQUEST_SPECIFICATION).contentType(ContentType.JSON)
                .body(todoToReplace).`when`().put(Constants.API_ENDPOINT_TODOS + "/" + todoToReplace.id).then()
                .statusCode(HttpStatus.SC_OK).extract().`as`(Todo::class.java)
    }

    private fun createTodo(todoToReplace: Todo): Todo {
        return RestAssured.given().spec(REQUEST_SPECIFICATION).contentType(ContentType.JSON)
                .body(todoToReplace).`when`().post(Constants.API_ENDPOINT_TODOS).then()
                .statusCode(HttpStatus.SC_OK).extract().`as`(Todo::class.java)
    }
}