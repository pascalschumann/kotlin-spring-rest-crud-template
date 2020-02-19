package com.pascalschumann.todoservice.api.controller

import com.pascalschumann.todoservice.api.configuration.Constants
import com.pascalschumann.todoservice.api.model.About
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Endpoint provides information about the service
 */
@RestController
@Api(value = "About", description = "the service", tags = ["About"])
class AboutController @Autowired constructor(private val buildProperties: BuildProperties,
                                             private val environment: Environment) {
    @GetMapping(Constants.API_ENDPOINT_ABOUT)
    fun about(): About {
        val about = About()
        // artifact's name from pom
        about.serviceName = buildProperties.name
        // artifact version
        about.serviceVersion = buildProperties.version
        // dateTime of build
        about.buildTime = buildProperties.time.toString()
        about.artifactId = buildProperties.artifact
        about.artifactGroup = buildProperties.group
        about.javaVersion = buildProperties["java.target"]
        about.springProfiles = environment.activeProfiles
        return about
    }

}



