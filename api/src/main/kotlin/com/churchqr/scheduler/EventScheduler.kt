package com.churchqr.scheduler

import com.churchqr.model.Event
import com.churchqr.model.enum.EventStatus
import com.churchqr.repository.EventRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId

import java.time.Instant
import java.util.*

@Component
class EventScheduler(private val eventRepository: EventRepository) {

    private val logger = LoggerFactory.getLogger(javaClass);

    /**
     * Opens a new event for registration
     */
    @Scheduled(cron = "0 12 * * 0")
    fun createEvent() {
        val nextSunday = LocalDateTime.now().plusDays(7).minusHours(1)
        val instant: Instant = nextSunday.atZone(ZoneId.systemDefault()).toInstant()
        val epochMilli = instant.toEpochMilli()

        val newEntity = Event(UUID.randomUUID(),"Sunday Service " + nextSunday.year + "-" + nextSunday.month + "-" + nextSunday.dayOfMonth, epochMilli, EventStatus.OPEN)
        eventRepository.save(newEntity)
        logger.info("EVENT OPENED: {}", newEntity)
    }

    /**
     * Closes the registration for the current available event
     */
    @Scheduled(cron = "0 17 * * 5")
    fun closeEventRegistration() {
        val currentEvent = eventRepository.findTop1ByStatusOrderByEventDateTimeDesc(EventStatus.OPEN)
        val updatedEvent: Event = currentEvent.copy(status = EventStatus.CLOSED)
        eventRepository.save(updatedEvent)
        logger.info("EVENT CLOSED: {}", updatedEvent)
    }

}