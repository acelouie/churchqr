package com.eusebioapps.api.service.reservation

import com.eusebioapps.api.model.Reservation

interface ReservationService {

    fun reserve(
        mobileNo: String,
        email: String,
        firstName: String,
        lastName: String,
        birthday: String,
        fullAddress: String,
        city: String,
        vaccinated: Boolean,
        volunteer: Boolean
    ) : Reservation

    fun scan(id: String) : Reservation

}