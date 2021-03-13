package com.churchqr.service

import com.churchqr.datasource.PersonDataSource
import com.churchqr.datasource.ReservationDataSource
import com.churchqr.model.Person
import com.churchqr.model.Reservation
import org.springframework.stereotype.Service

@Service
class ReservationService(private val dataSource: ReservationDataSource) {

    fun add(newEntity : Reservation) : Reservation {
        return dataSource.save(newEntity)
    }

}