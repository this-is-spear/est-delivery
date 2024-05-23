package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.out.LoadGiftCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UseGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import com.example.estdelivery.coupon.domain.member.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class EnrollCouponByMessageServiceTest : FreeSpec({
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val useGiftCouponCodeStatePort = mockk<UseGiftCouponCodeStatePort>()
    val loadGiftCouponStatePort = mockk<LoadGiftCouponStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()

    lateinit var enrollCouponByMessageService: EnrollCouponByMessageService

    beforeTest {
        enrollCouponByMessageService = EnrollCouponByMessageService(
            loadMemberStatePort,
            useGiftCouponCodeStatePort,
            loadGiftCouponStatePort,
            updateMemberStatePort,
            TransactionArea(),
        )
    }

    "메시지로 받은 쿠폰을 등록한다." {
        // given
        val member = 일건창()
        val giftCouponCode = GiftCouponCode.create()
        val coupon = 나눠준_비율_할인_쿠폰
        val giftCoupon = GiftCoupon(coupon)
        val updatedMember = slot<Member>()

        // when
        every { loadMemberStatePort.findMember(member.id) } returns member
        every { loadGiftCouponStatePort.findGiftCoupon(giftCouponCode) } returns giftCoupon
        every { useGiftCouponCodeStatePort.useBy(giftCouponCode) } returns Unit
        every { updateMemberStatePort.updateMembersCoupon(capture(updatedMember)) } returns Unit

        enrollCouponByMessageService.enroll(member.id, giftCouponCode)

        // then
        updatedMember.captured.showMyCouponBook() shouldContain coupon
    }

    "메시지로 받은 쿠폰 코드는 사용된적이 없으면 예외가 발생한다." {
        // given
        val member = 일건창()
        val giftCouponCode = GiftCouponCode.create()
        val coupon = 나눠준_비율_할인_쿠폰
        val giftCoupon = GiftCoupon(coupon, true)

        // when
        every { loadMemberStatePort.findMember(member.id) } returns member
        every { loadGiftCouponStatePort.findGiftCoupon(giftCouponCode) } returns giftCoupon

        // then
        shouldThrow<IllegalArgumentException> {
            enrollCouponByMessageService.enroll(member.id, giftCouponCode)
        }
    }
})
