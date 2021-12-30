package com.eusebioapps.api.repository

import com.eusebioapps.api.model.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, String> {
}
