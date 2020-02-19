package com.pascalschumann.todoservice.api.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Todo(@Id @GeneratedValue val id: Long, val name: String)