package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.command.GiftCouponCommand
import com.example.estdelivery.application.port.out.LoadCouponStatePort
import com.example.estdelivery.application.port.out.LoadMemberStatePort
import com.example.estdelivery.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.coupon.CouponType
import com.example.estdelivery.domain.fixture.이건창
import com.example.estdelivery.domain.fixture.일건창
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.mockk

class GiftCouponServiceTest : FreeSpec({
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val loadCouponStatePort = mockk<LoadCouponStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()
    val transactionArea = TransactionArea()
    lateinit var giftCouponService: GiftCouponService

    beforeTest {
        giftCouponService = GiftCouponService(
            loadMemberStatePort,
            loadCouponStatePort,
            updateMemberStatePort,
            transactionArea,
        )
    }

    "회원은 주변 회원에게 쿠폰을 선물할 수 있다" - {
        // given
        val coupon = Coupon.FixDiscountCoupon(
            1000,
            "쿠폰",
            "나눠줄 쿠폰",
            CouponType.IS_PUBLISHED,
            1L
        )
        val 주는자 = 이건창().apply {
            receiveCoupon(coupon)
        }
        val 받는자 = 일건창()
        val giftCouponCommand = GiftCouponCommand(1L, 주는자.id, 받는자.id)

        every { loadMemberStatePort.findById(주는자.id) } returns 주는자
        every { loadMemberStatePort.findById(받는자.id) } returns 받는자
        every { loadCouponStatePort.findById(giftCouponCommand.couponId) } returns coupon
        every { updateMemberStatePort.update(any()) } returns Unit

        // when &  then
        shouldNotThrow<Exception> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }

    "쿠폰이 존재해야 하고 사용하지 않은 쿠폰이어야 한다." {
        // given
        val 주는자 = 이건창()
        val 받는자 = 일건창()
        val giftCouponCommand = GiftCouponCommand(1L, 주는자.id, 받는자.id)

        every { loadMemberStatePort.findById(주는자.id) } returns 주는자
        every { loadMemberStatePort.findById(받는자.id) } returns 받는자

        // when &  then
        shouldThrow<IllegalArgumentException> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }

    "동일한 회원이어서는 안된다." {
        // given
        val coupon = Coupon.FixDiscountCoupon(
            1000,
            "쿠폰",
            "나눠줄 쿠폰",
            CouponType.IS_PUBLISHED,
            1L
        )
        val 주는자 = 이건창().apply {
            receiveCoupon(coupon)
        }
        val giftCouponCommand = GiftCouponCommand(1L, 주는자.id, 주는자.id)

        every { loadMemberStatePort.findById(주는자.id) } returns 주는자
        every { loadMemberStatePort.findById(주는자.id) } returns 주는자

        // when &  then
        shouldThrow<Exception> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }
})
