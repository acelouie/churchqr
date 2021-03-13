package com.churchqr.service

import com.churchqr.model.Person
import com.churchqr.model.Reservation
import com.churchqr.model.enum.EventStatus
import com.churchqr.repository.EventRepository
import com.churchqr.repository.PersonRepository
import com.churchqr.repository.ReservationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class ReservationService(
    private val eventRepository: EventRepository,
    private val personRepository: PersonRepository,
    private val reservationRepository: ReservationRepository
    ) {

    private val logger = LoggerFactory.getLogger(javaClass);

    fun reserve(mobileNo: String, email: String, firstName: String, lastName: String, birthday: String) : Reservation {

        // TODO Exception handling
        val currentEvent = eventRepository.findTop1ByStatusOrderByEventDateTimeDesc(EventStatus.OPEN)

        val eventList = reservationRepository.findByEvent(currentEvent)
        if(eventList.size >= 250) {
            // TODO Exception handling
            throw Exception("Event attendance limit reached")
        }

        var dbPerson: Person? = personRepository.findByMobileNo(mobileNo)
        var person = if(dbPerson != null) {
            val updatedPerson: Person = dbPerson.copy(email = email, firstName = firstName, lastName =  lastName, birthday = birthday)
            personRepository.save(updatedPerson)
        } else {
            val newPerson = Person(UUID.randomUUID(), mobileNo, email, firstName, lastName, birthday)
            personRepository.save(newPerson)
        }

        val localCurrentTime = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = localCurrentTime.atZone(ZoneId.systemDefault()).toInstant()
        val currentTime = instant.toEpochMilli()

        var newReservation = Reservation(UUID.randomUUID(), person, currentEvent, currentTime, null)
        newReservation = reservationRepository.save(newReservation)
        logger.info("NEW RESERVATION: {}", newReservation)
        return newReservation
    }

    fun scan(id: UUID) : Reservation {
        val localCurrentTime = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = localCurrentTime.atZone(ZoneId.systemDefault()).toInstant()
        val currentTime = instant.toEpochMilli()

        val dbReservation = reservationRepository.findById(id)
        if(dbReservation.isEmpty) {
            // TODO Exception handling
            throw Exception("Reservation is not found")
        }
        var updatedReservation: Reservation = dbReservation.get().copy(scannedDateTime = currentTime)
        updatedReservation = reservationRepository.save(updatedReservation)
        logger.info("SCANNED RESERVATION: {}", updatedReservation)
        return updatedReservation
    }

}