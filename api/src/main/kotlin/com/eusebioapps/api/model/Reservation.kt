package com.eusebioapps.api.model

import com.fasterxml.jackson.annotation.JsonProperty

import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table
data class Reservation(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(value = "id")
    @Column(insertable = true, updatable = false, length = 36)
    val id: String?,

    @NotNull
    @ManyToOne
    val person: Person,

    @NotNull
    @ManyToOne
    val event: Event,

    @NotNull
    val volunteer: Boolean,

    @NotNull
    val createdDateTime: Long,
    val scannedDateTime: Long?
)