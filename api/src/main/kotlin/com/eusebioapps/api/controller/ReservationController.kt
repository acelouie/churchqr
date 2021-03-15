package com.eusebioapps.api.controller

import com.eusebioapps.api.model.Reservation
import com.eusebioapps.api.service.reservation.ReservationServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.Column
import javax.validation.Valid
import javax.validation.constraints.*

@CrossOrigin
@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(private val reservationService: ReservationServiceImpl) : BaseController() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun scan(@Valid @RequestParam @NotNull id: String) : ResponseEntity<Reservation> {
        logger.debug("GET /api/v1/reservation [id={}]", id)
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.scan(id))
    }

    @PostMapping
    fun reserve(@Valid @RequestBody requestDto: ReserveRequestDto) : ResponseEntity<Reservation> {
        logger.debug("POST /api/v1/reservation [{}]", requestDto)
        return ResponseEntity.status(HttpStatus.OK).body(
            reservationService.reserve(requestDto.mobileNo, requestDto.email,
                requestDto.firstName, requestDto.lastName, requestDto.birthday,
                requestDto.fullAddress, requestDto.city))
    }

    data class ReserveRequestDto(
        @field:NotEmpty(message = "Mobile No is required")
        @field:Pattern(regexp = "^(09)\\d{9}", message = "Mobile No format should be 09XXXXXXXXX")
        val mobileNo: String,

        @field:NotEmpty(message = "Email is required")
        @field:Email(message = "Email should have a valid email format")
        @field:Size(max=50, message = "Email should not exceed 50 characters")
        val email: String,

        @field:NotEmpty(message = "First Name is required")
        @field:Size(max=50, message = "First Name should not exceed 50 characters")
        val firstName: String,

        @field:NotEmpty(message = "Last Name is required")
        @field:Size(max=50, message = "Last Name should not exceed 50 characters")
        val lastName: String,

        @field:NotEmpty(message = "Birthday is required")
        @field:DateTimeFormat(pattern = "yyyy-MM-dd")
        val birthday: String,

        @field:NotEmpty(message = "Address is required")
        @field:Size(max=255, message = "Address should not exceed to 255 characters")
        val fullAddress: String,

        @field:NotEmpty(message = "City is required")
        @field:Size(max=100, message = "City should not exceed 100 characters")
        val city: String

    )

}