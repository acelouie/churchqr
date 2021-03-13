package com.churchqr.controller

import com.churchqr.model.Reservation
import com.churchqr.service.ReservationService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@CrossOrigin
@RestController
class ReservationController(private val reservationService: ReservationService) {

    data class ReserveRequestDto(
        @NotNull @Pattern(regexp = "^(09)\\d{9}") val mobileNo: String,
        @NotNull @Email @Size(min=1,max=50) val email: String,
        @NotNull @Size(min=1,max=50) val firstName: String,
        @NotNull @Size(min=1, max=50) val lastName: String,
        @DateTimeFormat(pattern = "yyyy-MM-dd") val birthday: String
    )


    @PostMapping("/v1/reservation")
    fun post(@Valid @RequestBody requestDto: ReserveRequestDto) : ResponseEntity<Reservation> {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.reserve(requestDto.mobileNo, requestDto.email, requestDto.firstName, requestDto.lastName, requestDto.birthday))
    }

    @GetMapping("/v1/reservation")
    fun get(@RequestParam @Valid @NotNull id: String) : ResponseEntity<Reservation> {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.scan(id))
    }

}