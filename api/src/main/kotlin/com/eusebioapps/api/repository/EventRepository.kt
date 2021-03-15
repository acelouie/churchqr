package com.eusebioapps.api.repository

import com.eusebioapps.api.model.Event
import com.eusebioapps.api.model.enum.EventStatus
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, String> {

    fun findTop1ByStatusOrderByEventDateTimeDesc(status: EventStatus) : Event?

}