package com.churchqr.model

import com.churchqr.model.enum.EventStatus
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table
data class Event(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(value = "id")
    @Column(insertable = true, updatable = false, length = 36) val id: String?,
    @NotNull @Column(length = 50) val name: String,
    @NotNull val eventDateTime: Long,
    @NotNull @Enumerated(EnumType.STRING) @Column(length = 20) val status: EventStatus
)