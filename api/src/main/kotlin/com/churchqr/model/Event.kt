package com.churchqr.model

import com.churchqr.model.enum.EventStatus
import java.util.*
import javax.persistence.*

@Entity
class Event() {

    @get:Id
    @get:GeneratedValue
    private var id : UUID
        get() = id
        set(id) { this.id = id }

    @get:Column(length = 50)
    private var name : String
        get() = name
        set(name) { this.name = name }

    private var eventDateTime: Long
        get() = eventDateTime
        set(eventDateTime) { this.eventDateTime = eventDateTime }

    @get:Enumerated(EnumType.STRING)
    @get:Column(length = 15)
    private var status : EventStatus
        get() = status
        set(status) { this.status = status }

    constructor(name: String, eventDateTime: Long, status: EventStatus) : this() {
        this.name = name
        this.eventDateTime = eventDateTime
        this.status = status
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}