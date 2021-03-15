package com.eusebioapps.api.controller

import com.eusebioapps.api.model.Reservation
import com.eusebioapps.api.service.reservation.ReservationServiceImpl
import org.slf4j.LoggerFactory
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
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.reserve(requestDto.mobileNo, requestDto.email, requestDto.firstName, requestDto.lastName, requestDto.birthday))
    }

    data class ReserveRequestDto(
        @field:NotNull(message = "Mobile No is required")
        @field:Pattern(regexp = "^(09)\\d{9}", message = "Mobile No format should be 09XXXXXXXXX")
        val mobileNo: String,

        @field:NotNull(message = "Email is required")
        @field:Email(message = "Email should have a valid email format")
        @field:Size(min=1,max=50, message = "Email should be between 1 to 50 characters")
        val email: String,

        @field:NotNull(message = "First Name is required")
        @field:Size(min=1,max=50, message = "First Name should be between 1 to 50 characters")
        val firstName: String,

        @field:NotNull(message = "Last Name is required")
        @field:Size(min=1, max=50, message = "Last Name should be between 1 to 50 characters")
        val lastName: String,

        @field:NotNull(message = "Birthday is required")
        @field:DateTimeFormat(pattern = "yyyy-MM-dd")
        val birthday: String
    )

}