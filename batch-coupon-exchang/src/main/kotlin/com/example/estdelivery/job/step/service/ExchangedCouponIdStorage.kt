package com.example.estdelivery.job.step.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ExchangedCouponIdStorage {
    @Value("\${batch.expire-coupon-id}")
    var couponIdAfterExchange: Long = 0
        get() {
            require(field != 0L) { "CouponId is not set" }
            return field
        }
        set(value) {
            require(field == 0L) { "CouponId is already set" }
            field = value
        }
}
