package com.example.estdelivery.event

import com.example.estdelivery.event.dto.EventResponse
import com.example.estdelivery.event.entity.EventDiscountType
import com.example.estdelivery.event.entity.ProbabilityRange
import com.example.estdelivery.event.service.EventService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
open class EventBase {
    @Autowired
    lateinit var context: WebApplicationContext

    @MockkBean
    lateinit var eventService: EventService

    @BeforeEach
    fun setup() {
        every { eventService.findById(any()) } answers {
            val capturedId = firstArg<Long>()
            if (capturedId % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }

            EventResponse(
                capturedId,
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

        every { eventService.participate(any(), any()) } answers {
            if (secondArg<Long>() % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }
        }

        RestAssuredMockMvc.webAppContextSetup(this.context)
    }
}
