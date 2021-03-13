package com.churchqr.model

import java.util.*
import javax.persistence.*

@Entity
class Booking() {

    @get:Id @get:GeneratedValue private var id : UUID
        get() = id
        set(id) { this.id = id }

    @get:ManyToOne private var person: Person
        get() = person
        set(person) { this.person = person }

    private var createdDateTime: Long
        get() = createdDateTime
        set(reservationDateTime) { this.createdDateTime = reservationDateTime }

    private var scannedDateTime: Long
        get() = scannedDateTime
        set(scannedDateTime) { this.scannedDateTime = scannedDateTime }

    constructor(person: Person, createdDateTime: Long, scannedDateTime: Long) : this() {
        this.person = person
        this.createdDateTime = createdDateTime
        this.scannedDateTime = scannedDateTime
    }

    // TODO equals
    // TODO hashCode

}