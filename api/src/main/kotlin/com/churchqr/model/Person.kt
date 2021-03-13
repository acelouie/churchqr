package com.churchqr.model

import java.util.*
import javax.persistence.*

@Entity
@Table
data class Person(
    @Id @GeneratedValue val id: UUID,
    val mobileNo: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthday: String
)
