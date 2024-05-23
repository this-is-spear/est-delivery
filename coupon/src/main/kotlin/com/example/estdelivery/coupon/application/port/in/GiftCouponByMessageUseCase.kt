package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftMessageResponse

interface GiftCouponByMessageUseCase {
    fun sendGiftAvailableCoupon(memberId: Long, couponId: Long, giftMessage: String): GiftMessageResponse
}
