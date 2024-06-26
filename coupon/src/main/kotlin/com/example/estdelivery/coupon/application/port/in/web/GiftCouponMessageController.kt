package com.example.estdelivery.coupon.application.port.`in`.web

import com.example.estdelivery.coupon.application.port.`in`.EnrollCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftCouponResponses
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftMessageResponse
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val MEMBER_ID = "Member-ID"

@RestController
@RequestMapping("/gift-coupons")
class GiftCouponMessageController(
    private val findAvailableGiftCouponUseCase: FindAvailableGiftCouponUseCase,
    private val giftCouponByMessageUseCase: GiftCouponByMessageUseCase,
    private val enrollCouponByMessageUseCase: EnrollCouponByMessageUseCase,
) {
    @GetMapping
    fun findAvailableGiftCoupons(@RequestHeader(value = MEMBER_ID) memberId: Long): GiftCouponResponses =
        findAvailableGiftCouponUseCase.findAvailableGiftCoupon(memberId)


    @PostMapping("/send/{couponId}")
    fun sendGiftAvailableCoupon(
        @RequestHeader(value = MEMBER_ID) memberId: Long,
        @RequestParam message: String,
        @PathVariable couponId: Long,
    ): GiftMessageResponse =
        giftCouponByMessageUseCase.sendGiftAvailableCoupon(memberId, couponId, message)

    @GetMapping("/enroll/{code}")
    fun enrollGiftCoupon(
        @RequestHeader(value = MEMBER_ID) memberId: Long,
        @PathVariable code: String
    ) = enrollCouponByMessageUseCase.enroll(memberId, GiftCouponCode(code))
}
