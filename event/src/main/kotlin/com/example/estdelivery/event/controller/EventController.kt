package com.example.estdelivery.event.controller

import com.example.estdelivery.event.dto.EventResponse
import com.example.estdelivery.event.service.EventService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EventController(
    private val eventService: EventService
) {

    @GetMapping(
        value = ["/events/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getEvent(@PathVariable id: Long): EventResponse {
        return eventService.findById(id)
    }

    @PutMapping("/events/{id}/participants/{memberId}")
    fun participateEvent(@PathVariable id: Long, @PathVariable memberId: Long) {
        eventService.participate(id, memberId)
    }
}
