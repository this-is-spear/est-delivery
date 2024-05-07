package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.command.GiftCouponCommand
import com.example.estdelivery.application.port.out.LoadCouponStatePort
import com.example.estdelivery.application.port.out.LoadMemberStatePort
import com.example.estdelivery.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.application.port.out.state.CouponState
import com.example.estdelivery.application.port.out.state.CouponStateAmountType
import com.example.estdelivery.application.port.out.state.CouponStateType
import com.example.estdelivery.application.port.out.state.MemberState
import com.example.estdelivery.application.utils.TransactionArea
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
        val receiverId = 3L
        val senderId = 2L
        val giftCouponCommand = GiftCouponCommand(1L, senderId, receiverId)
        val couponState = CouponState(
            "쿠폰",
            "나눠줄 쿠폰",
            CouponStateAmountType.FIX,
            CouponStateType.HANDOUT,
            1000,
            1L
        )
        val receiverCouponBook = listOf(couponState.toCoupon())

        every { loadMemberStatePort.findById(senderId) } returns MemberState("주는자", receiverCouponBook, senderId)
        every { loadMemberStatePort.findById(receiverId) } returns MemberState("받는자", listOf(), receiverId)
        every { loadCouponStatePort.findByCouponId(giftCouponCommand.couponId) } returns couponState
        every { updateMemberStatePort.update(any()) } returns Unit

        // when &  then
        shouldNotThrow<Exception> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }

    "쿠폰이 존재해야 하고 사용하지 않은 쿠폰이어야 한다." {
        // given
        val receiverId = 3L
        val senderId = 2L
        val giftCouponCommand = GiftCouponCommand(1L, senderId, receiverId)

        every { loadMemberStatePort.findById(senderId) } returns MemberState("주는자", listOf(), senderId)
        every { loadMemberStatePort.findById(receiverId) } returns MemberState("받는자", listOf(), receiverId)

        // when &  then
        shouldThrow<IllegalArgumentException> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }

    "동일한 회원이어서는 안된다." {
        // given
        val receiverId = 3L
        val senderId = 3L
        val giftCouponCommand = GiftCouponCommand(1L, senderId, receiverId)
        val couponState = CouponState(
            "쿠폰",
            "나눠줄 쿠폰",
            CouponStateAmountType.FIX,
            CouponStateType.HANDOUT,
            1000,
            1L
        )
        val receiverCouponBook = listOf(couponState.toCoupon())

        every { loadMemberStatePort.findById(senderId) } returns MemberState("주는자", receiverCouponBook, senderId)
        every { loadMemberStatePort.findById(receiverId) } returns MemberState("받는자", listOf(), receiverId)

        // when &  then
        shouldThrow<Exception> {
            giftCouponService.giftCoupon(giftCouponCommand)
        }
    }
})
