package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.coupon.Coupon

interface LoadCouponStatePort {
    fun exists(couponId: Long): Boolean

    fun findById(couponId: Long): Coupon
}
