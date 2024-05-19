package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.ValidateGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.일건창
import com.example.estdelivery.coupon.domain.member.Member
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class GiftCouponByMessageServiceTest : FreeSpec({
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val createGiftCouponMessageStatePort = mockk<CreateGiftCouponMessageStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()
    val validateGiftCouponCodeStatePort = mockk<ValidateGiftCouponCodeStatePort>()

    lateinit var giftCouponByMessageService: GiftCouponByMessageService
    beforeTest {
        giftCouponByMessageService = GiftCouponByMessageService(
            loadMemberStatePort,
            createGiftCouponMessageStatePort,
            updateMemberStatePort,
            validateGiftCouponCodeStatePort,
            TransactionArea()
        )
    }

    "쿠폰 선물하기 위한 메시지를 전달받는다." {
        // given
        val 선물할_쿠폰 = 나눠준_비율_할인_쿠폰
        val 일건창 = 일건창().apply {
            receiveCoupon(선물할_쿠폰)
        }
        val 변경된_회원_정보 = slot<Member>()
        val 선물_메시지 = "선물 메시지"
        val 쿠폰_코드 = GiftCouponCode.create()

        // when
        every { loadMemberStatePort.findMember(일건창.id) } returns 일건창
        every { createGiftCouponMessageStatePort.create(일건창, 선물할_쿠폰, 선물_메시지, any()) } returns GiftMessage(
            일건창,
            선물_메시지,
            쿠폰_코드,
            GiftCoupon(선물할_쿠폰)
        )
        every { validateGiftCouponCodeStatePort.validate(any()) } returns true
        every { updateMemberStatePort.updateMembersCoupon(capture(변경된_회원_정보)) } returns Unit

        val giftAvailableCoupon =
            giftCouponByMessageService.sendGiftAvailableCoupon(일건창.id, 선물할_쿠폰.id!!, 선물_메시지)

        // then
        변경된_회원_정보.captured.showMyCouponBook().find { it.id == 선물할_쿠폰.id } shouldBe null
        giftAvailableCoupon.sender shouldBe 일건창
        giftAvailableCoupon.giftCouponCode shouldBe 쿠폰_코드
    }
})
