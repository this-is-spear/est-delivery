package com.example.estdelivery.job.step.service

import com.example.estdelivery.domain.CouponStateAmountType

interface CouponService {
    fun createCouponToBeExchange(
        name: String,
        description: String,
        amountType: CouponStateAmountType,
        amount: Int
    ): Long

    fun createExchangeHistory(expiredCouponId: Long, createdCouponId: Long, jobExecutionId: Long)
}
