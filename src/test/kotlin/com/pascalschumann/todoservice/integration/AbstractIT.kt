package com.pascalschumann.todoservice.integration

import com.pascalschumann.todoservice.Application
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration


/**
 * This class initializes the test environment.
 */
// @RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [Application::class])
// For gradle: mark it as integration test (and its subclass) to avoid executing them with junitPlattform
class AbstractIT {
    protected val BASE_URL = "http://localhost:8080"
    @Value("\${server.port}")
    var port: Int? = null

    protected var REQUEST_SPECIFICATION: RequestSpecification? = null
    @BeforeEach
    fun before() {
        REQUEST_SPECIFICATION = RequestSpecBuilder().setBaseUri(BASE_URL)
                .addFilter(ResponseLoggingFilter())
                .addFilter(RequestLoggingFilter()).build()
    }
}
