package com.example.estdelivery.job.step.service

import org.springframework.stereotype.Component

@Component
class ExchangedCouponIdStorage {
    var couponId: Long = 0
        get() {
            require(field != 0L) { "CouponId is not set" }
            return field
        }
}
