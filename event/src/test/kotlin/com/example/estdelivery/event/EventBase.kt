package com.example.estdelivery.event

import com.example.estdelivery.event.controller.EventController
import com.example.estdelivery.event.dto.EventResponse
import com.example.estdelivery.event.entity.EventDiscountType
import com.example.estdelivery.event.entity.ProbabilityRange
import com.example.estdelivery.event.service.EventService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach

open class EventBase {
    @BeforeEach
    fun setup() {
        val eventService = mockk<EventService>()
        val id = slot<Long>()
        every { eventService.findById(capture(id)) } answers {
            EventResponse(
                id.captured,
                "이벤트 설명",
                true,
                EventDiscountType.FIXED,
                listOf(
                    ProbabilityRange(1000, 1500, 0.5),
                    ProbabilityRange(1600, 2000, 0.3),
                    ProbabilityRange(2100, 2500, 0.2),
                ),
                1000,
                2500,
                listOf(1, 2, 3),
            )
        }

        every { eventService.participate(any(), any()) } returns Unit
        RestAssuredMockMvc.standaloneSetup(EventController(eventService))
    }
}
