package com.eusebioapps.api.service.reservation

import com.eusebioapps.api.model.Person
import com.eusebioapps.api.model.Reservation
import com.eusebioapps.api.model.enum.EventStatus
import com.eusebioapps.api.repository.EventRepository
import com.eusebioapps.api.repository.PersonRepository
import com.eusebioapps.api.repository.ReservationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class ReservationServiceImpl(
    private val eventRepository: EventRepository,
    private val personRepository: PersonRepository,
    private val reservationRepository: ReservationRepository
    ) : ReservationService {

    private val logger = LoggerFactory.getLogger(javaClass);

    override fun reserve(mobileNo: String, email: String, firstName: String, lastName: String, birthday: String) : Reservation {

        // TODO Exception handling
        val currentEvent = eventRepository.findTop1ByStatusOrderByEventDateTimeDesc(EventStatus.OPEN)

        val eventList = reservationRepository.findByEvent(currentEvent)
        if(eventList.size >= 250) {
            // TODO Exception handling
            throw Exception("Event attendance limit reached")
        }

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        val convertedBirthday: LocalDate = LocalDate.parse(birthday, dtf)
        // TODO birthday checker

        var dbPerson: Person? = personRepository.findByMobileNo(mobileNo)
        var person = if(dbPerson != null) {
            val updatedPerson: Person = dbPerson.copy(email = email, firstName = firstName, lastName =  lastName, birthday = convertedBirthday)
            personRepository.save(updatedPerson)
        } else {
            val newPerson = Person(null, mobileNo, email, firstName, lastName, convertedBirthday)
            personRepository.save(newPerson)
        }

        val localCurrentTime = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = localCurrentTime.atZone(ZoneId.systemDefault()).toInstant()
        val currentTime = instant.toEpochMilli()

        var newReservation = Reservation(null, person, currentEvent, currentTime, null)
        newReservation = reservationRepository.save(newReservation)
        logger.info("NEW RESERVATION: {}", newReservation)
        return newReservation
    }

    override fun scan(id: String) : Reservation {
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
        logger.info("scan: {}", updatedReservation)
        return updatedReservation
    }

}