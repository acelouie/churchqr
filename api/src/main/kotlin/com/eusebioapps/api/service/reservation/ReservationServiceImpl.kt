package com.eusebioapps.api.service.reservation

import com.eusebioapps.api.model.Person
import com.eusebioapps.api.model.Reservation
import com.eusebioapps.api.model.enum.EventStatus
import com.eusebioapps.api.model.exception.BusinessRuleException
import com.eusebioapps.api.repository.EventRepository
import com.eusebioapps.api.repository.PersonRepository
import com.eusebioapps.api.repository.ReservationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.*
import java.time.format.DateTimeFormatter

@Service
class ReservationServiceImpl(
    private val eventRepository: EventRepository,
    private val personRepository: PersonRepository,
    private val reservationRepository: ReservationRepository
    ) : ReservationService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun reserve(mobileNo: String, email: String, firstName: String, lastName: String, birthday: String, fullAddress: String, city: String) : Reservation {
        logger.debug("reserve: (start) [mobileNo={},email={},firstName={},lastName={},birthday={},fullAddress={},city{}]",
            mobileNo, email, firstName, lastName, birthday, fullAddress, city)

        // Validate event
        val currentEvent = eventRepository.findTop1ByStatusOrderByEventDateTimeDesc(EventStatus.OPEN)
            ?: throw BusinessRuleException("There is no event with on-going registration. Please create a new event.")
        val eventList = reservationRepository.findByEvent(currentEvent)

        logger.debug("reserve: (validating current event) [size={},event={}]", eventList.size, currentEvent)
        if(eventList.size >= 250) {
            throw BusinessRuleException("Event attendance limit reached. Only 250 people are allowed.")
        }

        // Validate birthday
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val convertedBirthday: LocalDate = LocalDate.parse(birthday, dtf)
        val age = Period.between(convertedBirthday, LocalDate.now()).years
        logger.debug("reserve: (validating age) [birthday={},age={}]", convertedBirthday, age)
        if(age < 15 || age > 65) {
            throw BusinessRuleException("Sorry, only people ages 16 to 65 are allowed.")
        }

        // Add or update person
        val dbPerson: Person? = personRepository.findByMobileNo(mobileNo)
        val person = if(dbPerson != null) {
            // Update details of the person
            val updatedPerson: Person = dbPerson.copy(email = email, firstName = firstName, lastName =  lastName,
                                                    birthday = convertedBirthday, fullAddress = fullAddress, city = city)
            logger.debug("reserve: (person updated) [{}]", updatedPerson)
            personRepository.save(updatedPerson)
        } else {
            val newPerson = Person(null, mobileNo, email, firstName, lastName, convertedBirthday, fullAddress, city)
            logger.debug("reserve: (person saved) [{}]", newPerson)
            personRepository.save(newPerson)
        }

        // Validate if person is already reserved
        val existingReservation = reservationRepository.findByPersonAndEvent(person, currentEvent)
        if(existingReservation != null) {
            logger.debug("reserve: (done - has existing) [person={},event={}]", person, currentEvent)
            return existingReservation
        }

        // Save reservation
        val localCurrentTime = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = localCurrentTime.atZone(ZoneId.systemDefault()).toInstant()
        val currentTime = instant.toEpochMilli()

        var newReservation = Reservation(null, person, currentEvent, currentTime, null)
        newReservation = reservationRepository.save(newReservation)
        logger.info("reserve: (done - saved) {}", newReservation)
        return newReservation
    }

    override fun scan(id: String) : Reservation {
        val localCurrentTime = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = localCurrentTime.atZone(ZoneId.systemDefault()).toInstant()
        val currentTime = instant.toEpochMilli()

        val dbReservation = reservationRepository.findById(id)
        if(dbReservation.isEmpty) {
            throw BusinessRuleException("Reservation does not exist")
        }
        var updatedReservation: Reservation = dbReservation.get().copy(scannedDateTime = currentTime)
        updatedReservation = reservationRepository.save(updatedReservation)
        logger.info("scan: {}", updatedReservation)
        return updatedReservation
    }

}