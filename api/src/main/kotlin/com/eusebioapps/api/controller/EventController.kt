package com.eusebioapps.api.controller

import com.eusebioapps.api.model.Event
import com.eusebioapps.api.service.event.EventService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@CrossOrigin
@RestController
@RequestMapping("/api/v1/event")
class EventController(private val eventService: EventService) : BaseController() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping("/current")
    fun findCurrent() : ResponseEntity<Event> {
        logger.debug("GET /api/v1/event/current")
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findCurrentEvent())
    }

    @PostMapping
    fun create(@Valid @RequestBody eventDto: CreateEventDto) : ResponseEntity<Event> {
        logger.debug("POST /api/v1/event [{}]", eventDto)
        return ResponseEntity.status(HttpStatus.OK).body(eventService.createNewEvent(eventDto.name, eventDto.eventDateTime))
    }

    @PutMapping
    fun closeEvent(@RequestParam @NotEmpty id: String) : ResponseEntity<Event> {
        logger.debug("PUT /api/v1/event/")
        return ResponseEntity.status(HttpStatus.OK).body(eventService.closeEventRegistration(id))
    }

    data class CreateEventDto(

        @field:NotEmpty(message="Name is mandatory")
        @field:Size(max=50, message="Name should not exceed 50 characters")
        val name: String,

        @field:Min(1, message="Event Date & Time is mandatory")
        val eventDateTime: Long

    )

}