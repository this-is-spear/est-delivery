package com.example.estdelivery.coupon.application.port.out.adapter.infra

import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.UpdateParticipatedMembersState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpServerErrorException

@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = ["com.example.estdelivery.event:event:1.0-SNAPSHOT:8083"]
)
class EventInfraAdapterTest(
    @Autowired
    private val eventInfraAdapter: EventInfraAdapter
) {
    @Test
    fun `이벤트를 조회한다`() {
        val event = eventInfraAdapter.findEvent(1)
        assertEquals(1, event.id)
    }

    @Test
    fun `회원을 이벤트에 참가시킨다`() {
        val participatedMembersState = UpdateParticipatedMembersState(1, 1)
        eventInfraAdapter.participateMember(participatedMembersState)
    }

    @Test
    fun `1000번 식별자를 조회하면 400 에러가 발생한다`() {
        assertAll(
            { assertThrows<BadRequest> { eventInfraAdapter.findEvent(1000) } },
            {
                assertThrows<BadRequest> {
                    eventInfraAdapter.participateMember(UpdateParticipatedMembersState(1, 1000))
                }
            }
        )
    }

    @Test
    fun `1001번 식별자를 조회하면 429 에러가 발생한다`() {
        assertAll(
            {
                assertThrows<HttpClientErrorException.TooManyRequests> {
                    eventInfraAdapter.findEvent(1001)
                }
            },
            {
                assertThrows<HttpClientErrorException.TooManyRequests> {
                    eventInfraAdapter.participateMember(UpdateParticipatedMembersState(1, 1001))
                }
            }
        )
    }

    @Test
    fun `1100번 식별자를 조회하면 500 에러가 발생한다`() {
        assertAll(
            {
                assertThrows<HttpServerErrorException.InternalServerError> {
                    eventInfraAdapter.findEvent(1100)
                }
            },
            {
                assertThrows<HttpServerErrorException.InternalServerError> {
                    eventInfraAdapter.participateMember(UpdateParticipatedMembersState(1, 1100))
                }
            }
        )
    }

    @Test
    fun `1101번 식별자를 조회하면 503 에러가 발생한다`() {
        assertAll(
            {
                assertThrows<HttpServerErrorException.ServiceUnavailable> {
                    eventInfraAdapter.findEvent(1101)
                }
            },
            {
                assertThrows<HttpServerErrorException.ServiceUnavailable> {
                    eventInfraAdapter.participateMember(UpdateParticipatedMembersState(1, 1101))
                }
            }
        )
    }
}
