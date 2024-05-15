package com.example.estdelivery.coupon.application.port.out.adapter.infra

import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.UpdateParticipatedMembersState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

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
        assertEquals(1L, event.id)
    }

    @Test
    fun `회원을 이벤트에 참가시킨다`() {
        val participatedMembersState = UpdateParticipatedMembersState(1, 1)
        eventInfraAdapter.participateMember(participatedMembersState)
    }
}
