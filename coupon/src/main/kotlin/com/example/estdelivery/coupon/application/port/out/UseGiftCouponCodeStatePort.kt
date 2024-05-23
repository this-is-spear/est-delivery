package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode

interface UseGiftCouponCodeStatePort {
    fun useBy(giftCouponCode: GiftCouponCode)
}
