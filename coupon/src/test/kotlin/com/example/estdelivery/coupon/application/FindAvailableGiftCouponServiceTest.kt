package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.domain.fixture.게시된_고정_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.이벤트_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class FindAvailableGiftCouponServiceTest : FreeSpec({
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    lateinit var findAvailableGiftCouponService: FindAvailableGiftCouponService
    beforeTest {
        findAvailableGiftCouponService = FindAvailableGiftCouponService(loadMemberStatePort)
    }

    "회원이 선물 가능한 쿠폰을 조회한다" {
        // given
        val memberId = 1L
        val 일건창 = 일건창().apply {
            receiveCoupon(나눠준_비율_할인_쿠폰)
            receiveCoupon(게시된_고정_할인_쿠폰)
            receiveCoupon(이벤트_쿠폰)
        }
        every { loadMemberStatePort.findMember(memberId) } returns 일건창

        // when
        val availableGiftCoupons = findAvailableGiftCouponService.findAvailableGiftCoupon(memberId)

        // then
        availableGiftCoupons.map { it.coupon } shouldBe 일건창.showMyCouponBook().filter { !it.isPublished() }
    }
})
