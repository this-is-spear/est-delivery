package com.example.estdelivery.coupon.application.port.out.adapter.infra

import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.EventState
import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.UpdateParticipatedMembersState
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class EventInfraAdapter(
    private val eventClient: RestClient,
) {
    fun participateMember(event: UpdateParticipatedMembersState) =
        eventClient.put()
            .uri("/events/{eventId}/participants/{participatedMemberId}", event.id, event.participatedMemberId)
            .body(event)
            .retrieve()
            .body(Void::class.java)

    fun findEvent(eventId: Long): EventState =
        eventClient.get()
            .uri("/events/{eventId}", eventId)
            .retrieve()
            .body(EventState::class.java)
            ?: throw RuntimeException("Event not found")
}
