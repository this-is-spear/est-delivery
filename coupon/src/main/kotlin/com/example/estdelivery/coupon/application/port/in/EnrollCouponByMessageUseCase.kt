package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode

interface EnrollCouponByMessageUseCase {
    fun enroll(memberId: Long, code: GiftCouponCode)
}
