package com.pascalschumann.todoservice.api.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * Enables and configures the swagger documentation
 * http://localhost:8080/swagger-ui.html
 */
@EnableSwagger2
@Configuration
class SwaggerConfiguration @Autowired constructor(private val buildProperties: BuildProperties) {
    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController::class.java))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        val serviceName = buildProperties.name
        return ApiInfoBuilder().title(serviceName).description(
                "Description: https://github.com/pascalschumann/${Constants.REPOSITORY_NAME}/blob/master/README.md")
                .license("License: Apache License 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .termsOfServiceUrl("").version("1.0.0")
                .contact(Contact("Bug report",
                        "https://github.com/pascalschumann/${Constants.REPOSITORY_NAME}/issues/new",
                        ""))
                .build()
    }

}