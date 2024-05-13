package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.application.port.`in`.command.UseCouponCommand

interface UseCouponUseCase {
    fun useCoupon(useCouponCommand: UseCouponCommand)
}
