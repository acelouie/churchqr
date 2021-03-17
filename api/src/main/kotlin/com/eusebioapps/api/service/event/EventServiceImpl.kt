package com.eusebioapps.api.service.event

import com.eusebioapps.api.model.Event
import com.eusebioapps.api.model.enum.EventStatus
import com.eusebioapps.api.model.exception.BusinessRuleException
import com.eusebioapps.api.repository.EventRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class EventServiceImpl(private val eventRepository: EventRepository) : EventService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun findCurrentEvent(): Event {
        val currentEvent = eventRepository.findTop1ByOrderByEventDateTimeDesc()
            ?: throw BusinessRuleException("There is no event with on-going registration. Please create a new event.");
        logger.debug("findCurrentEvent: [{}]", currentEvent)
        return currentEvent
    }

    override fun createNewEvent(name: String, eventDateTime: Long): Event {
        val newEvent = eventRepository.save(Event(null,  name, eventDateTime, EventStatus.OPEN))
        logger.debug("createNewEvent: [{}]", newEvent)
        return newEvent
    }

    override fun closeEventRegistration(id: String): Event {
        val currentEvent = eventRepository.findById(id)
        if(currentEvent.isEmpty) {
            throw BusinessRuleException("Event not found.")
        }
        var updatedEvent: Event = currentEvent.get().copy(status = EventStatus.CLOSED)
        updatedEvent = eventRepository.save(updatedEvent)
        logger.debug("closeEventRegistration: [{}]", updatedEvent)
        return updatedEvent
    }

}