package com.churchqr.model

import java.util.*
import javax.persistence.*

@Entity
class Reservation() {

    @get:Id @get:GeneratedValue private var id : UUID
        get() = id
        set(id) { this.id = id }

    @get:Column(length = 10)
    private var reservationCode: String
        get() = reservationCode
        set(reservationCode) { this.reservationCode = reservationCode }

    @get:ManyToOne private var person: Person
        get() = person
        set(person) { this.person = person }

    @get:ManyToOne private var event: Event
        get() = event
        set(event) { this.event = event }

    private var createdDateTime: Long
        get() = createdDateTime
        set(createdDateTime) { this.createdDateTime = createdDateTime }

    private var scannedDateTime: Long
        get() = scannedDateTime
        set(scannedDateTime) { this.scannedDateTime = scannedDateTime }

    constructor(reservationCode: String, person: Person, event: Event, createdDateTime: Long, scannedDateTime: Long) : this() {
        this.reservationCode = reservationCode
        this.person = person
        this.event = event
        this.createdDateTime = createdDateTime
        this.scannedDateTime = scannedDateTime
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reservation

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}