package com.churchqr.repository

import com.churchqr.model.Event
import com.churchqr.model.enum.EventStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<Event, UUID> {

    fun findTop1ByStatusOrderByEventDateTimeDesc(status: EventStatus) : Event

}