package com.eusebioapps.api.repository

import com.eusebioapps.api.model.Event
import com.eusebioapps.api.model.Person
import com.eusebioapps.api.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, String> {

    fun findByEvent(event: Event) : List<Reservation>
    fun findByPersonAndEvent(person: Person, event: Event) : Reservation?

}