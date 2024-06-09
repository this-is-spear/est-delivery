package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.CouponRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.ShopOwnerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = [
        "com.example.estdelivery.member:member:1.0-SNAPSHOT:8081",
        "com.example.estdelivery.event:event:1.0-SNAPSHOT:8083",
    ]
)
class EventAdapterTest(
    @Autowired
    private val couponRepository: CouponRepository,
    @Autowired
    private val memberAdapter: MemberAdapter,
    @Autowired
    private val eventAdapter: EventAdapter,
    @Autowired
    private val shopOwnerRepository: ShopOwnerRepository,
) {

    @BeforeEach
    fun setUp() {
        shopOwnerRepository.deleteAll()
        couponRepository.deleteAll()
    }

    @Test
    fun `이벤트를 조회한다`() {
        val event = eventAdapter.findEvent(1)
        assertEquals(1L, event.id)
    }

    @Test
    fun `회원을 이벤트에 참가시킨다`() {
        val event = eventAdapter.findEvent(1)
        val member = memberAdapter.findMember(1)
        eventAdapter.participate(event, member)
    }
}
