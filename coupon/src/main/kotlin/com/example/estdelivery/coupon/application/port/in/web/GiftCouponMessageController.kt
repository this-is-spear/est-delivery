package com.example.estdelivery.coupon.application.port.`in`.web

import com.example.estdelivery.coupon.application.port.`in`.FindAvailableGiftCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftCouponResponse
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftCouponResponses
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftMessageResponse
import com.example.estdelivery.coupon.domain.coupon.Coupon.FixDiscountCoupon
import com.example.estdelivery.coupon.domain.coupon.Coupon.RateDiscountCoupon
import java.net.URL
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gift-coupons")
class GiftCouponMessageController(
    private val findAvailableGiftCouponUseCase: FindAvailableGiftCouponUseCase,
    private val giftCouponByMessageUseCase: GiftCouponByMessageUseCase,
) {
    @GetMapping
    fun findAvailableGiftCoupons(@RequestHeader(value = "Member-ID") memberId: Long): GiftCouponResponses =
        findAvailableGiftCouponUseCase.findAvailableGiftCoupon(memberId)
            .map {
                GiftCouponResponse(
                    id = it.coupon.id!!,
                    name = it.coupon.name,
                    discountAmount = if (it.coupon is FixDiscountCoupon) it.coupon.discountAmount
                    else (it.coupon as RateDiscountCoupon).discountRate,
                    discountType = it.coupon.couponType
                )
            }.let {
                GiftCouponResponses(it)
            }

    @PostMapping("/send/{couponId}")
    fun sendGiftAvailableCoupon(
        @RequestHeader(value = "Member-ID") memberId: Long,
        @RequestParam message: String,
        @PathVariable couponId: Long,
    ): GiftMessageResponse =
        giftCouponByMessageUseCase.sendGiftAvailableCoupon(
            memberId,
            couponId,
            message,
        ).let {
            GiftMessageResponse(
                senderName = it.sender.name,
                description = it.giftMessage,
                enrollHref = URL("http", "localhost", 8080, "/gift-coupons/enroll/${it.giftCode}")
            )
        }
}
