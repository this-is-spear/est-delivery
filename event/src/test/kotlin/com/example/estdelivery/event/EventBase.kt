package com.example.estdelivery.event

import com.example.estdelivery.event.controller.BadRequestException
import com.example.estdelivery.event.controller.InternalServerErrorException
import com.example.estdelivery.event.controller.ServiceUnavailableException
import com.example.estdelivery.event.controller.TooManyRequestsException
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
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
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
            val id = firstArg<Long>()
            handleDefaultException(id)

            EventResponse(
                id,
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
            val id = secondArg<Long>()
            handleDefaultException(id)
        }

        RestAssuredMockMvc.webAppContextSetup(this.context)
    }

    private fun handleDefaultException(id: Long) {
        if (id == 1000L) {
            throw BadRequestException()
        }

        if (id == 1001L) {
            throw TooManyRequestsException()
        }

        if (id == 1100L) {
            throw InternalServerErrorException()
        }

        if (id == 1101L) {
            throw ServiceUnavailableException()
        }
    }
}
