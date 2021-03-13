package com.churchqr.repository

import com.churchqr.model.Event
import com.churchqr.model.enum.EventStatus
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, String> {

    fun findTop1ByStatusOrderByEventDateTimeDesc(status: EventStatus) : Event

}