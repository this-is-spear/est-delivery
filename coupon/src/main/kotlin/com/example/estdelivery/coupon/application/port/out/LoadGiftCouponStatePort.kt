package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode

interface LoadGiftCouponStatePort {
    fun findGiftCoupon(giftCouponCode: GiftCouponCode): GiftCoupon
}
