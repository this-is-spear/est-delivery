package com.example.estdelivery.event.service

import com.example.estdelivery.event.dto.EventResponse
import com.example.estdelivery.event.repository.EventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository,
) {
    fun findById(id: Long): EventResponse {
        val event = eventRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Event not found. id=$id")
        return EventResponse(
            id = event.id,
            description = event.description,
            isProgress = event.isProgress,
            discountType = event.disCountType,
            discountRangeMin = event.disCountRangeMin,
            discountRangeMax = event.disCountRangeMax,
            intervalsProbability = event.intervalsProbability,
            participatedMembers = event.participatedMembers,
        )
    }

    fun participate(id: Long, memberId: Long) {
        val event = eventRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Event not found. id=$id")
        event.participatedMembers += memberId
        eventRepository.save(event)
    }
}
