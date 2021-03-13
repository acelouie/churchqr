package com.churchqr.datasource

import com.churchqr.model.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ReservationDataSource : JpaRepository<Reservation, UUID> {
}