package com.churchqr.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table
data class Person(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(value = "id")
    @Column(insertable = true, updatable = false, length = 36)
    val id: String?,

    @NotNull
    @Column(unique = true, length = 25)
    @Size(min = 1, max = 25)
    val mobileNo: String,

    @NotNull
    @Column(unique = true, length = 100)
    @Size(min = 1, max = 100)
    @Email
    val email: String,

    @NotNull
    @Column(length = 100)
    @Size(min = 1, max = 100)
    val firstName: String,

    @NotNull
    @Column(length = 100)
    @Size(min = 1, max = 100)
    val lastName: String,

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate

)
