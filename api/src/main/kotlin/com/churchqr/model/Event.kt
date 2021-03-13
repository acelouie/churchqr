package com.churchqr.model

import com.churchqr.model.enum.EventStatus
import java.util.*
import javax.persistence.*

@Entity
@Table
data class Event(
    @Id @GeneratedValue val id: UUID,
    val name: String,
    val eventDateTime: Long,
    val status: EventStatus
)