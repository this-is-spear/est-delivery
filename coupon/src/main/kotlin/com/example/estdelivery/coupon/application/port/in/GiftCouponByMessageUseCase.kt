package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.domain.coupon.GiftMessage

interface GiftCouponByMessageUseCase {
    fun sendGiftAvailableCoupon(memberId: Long, couponId: Long, giftMessage: String): GiftMessage
}
