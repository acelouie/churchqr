package com.churchqr.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class ReservationController : BaseController() {

    @PostMapping("/v1/reservation/reserve")
    fun reserve() {

    }

    @PostMapping("/v1/reservation/scan")
    fun scan() {

    }

}