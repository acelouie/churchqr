package com.eusebioapps.api.service.event

import com.eusebioapps.api.model.Event

interface EventService {

    fun findCurrentEvent() : Event

    fun createNewEvent(name: String, eventDateTime: Long) : Event

    fun closeEventRegistration(id: String) : Event

}