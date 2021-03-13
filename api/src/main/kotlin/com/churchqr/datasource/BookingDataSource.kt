package com.churchqr.datasource

import com.churchqr.model.Booking
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookingDataSource : JpaRepository<Booking, UUID> {
}