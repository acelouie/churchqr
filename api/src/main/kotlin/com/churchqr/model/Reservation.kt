package com.churchqr.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table
data class Reservation(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(value = "id")
    @Column(insertable = true, updatable = false, length = 36) val id: String?,
    @ManyToOne val person: Person,
    @ManyToOne val event: Event,
    val createdDateTime: Long,
    val scannedDateTime: Long?
)