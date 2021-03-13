package com.churchqr.repository

import com.churchqr.model.Event
import com.churchqr.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReservationRepository : JpaRepository<Reservation, UUID> {

    fun findByEvent(event: Event) : List<Reservation>

}