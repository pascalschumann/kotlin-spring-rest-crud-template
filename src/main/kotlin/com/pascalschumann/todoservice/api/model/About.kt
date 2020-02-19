package com.pascalschumann.todoservice.api.model

data class About(var artifactId: String?, var artifactGroup: String?, var serviceName: String?,
                 var serviceVersion: String?, var buildTime: String?, var javaVersion: String?,
                 var springProfiles: Array<String>?) {
    constructor() : this(null, null, null, null, null, null, null)
}