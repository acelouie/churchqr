package com.eusebioapps.api.service.reservation

import com.eusebioapps.api.model.Reservation

interface ReservationService {

    fun reserve(mobileNo: String, email: String, firstName: String, lastName: String, birthday: String) : Reservation

    fun scan(id: String) : Reservation

}