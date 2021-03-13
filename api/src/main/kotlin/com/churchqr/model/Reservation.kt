package com.churchqr.model

import java.util.*
import javax.persistence.*

@Entity
@Table
data class Reservation(
    @Id @GeneratedValue val id: UUID,
    @ManyToOne val person: Person,
    @ManyToOne val event: Event,
    val createdDateTime: Long,
    val scannedDateTime: Long?
)