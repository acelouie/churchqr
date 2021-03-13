package com.churchqr.repository

import com.churchqr.model.Event
import com.churchqr.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationRepository : JpaRepository<Reservation, String> {

    fun findByEvent(event: Event) : List<Reservation>

}