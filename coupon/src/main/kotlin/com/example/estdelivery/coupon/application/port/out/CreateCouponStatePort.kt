package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.coupon.Coupon

interface CreateCouponStatePort {
    fun create(coupon: Coupon): Coupon
}
