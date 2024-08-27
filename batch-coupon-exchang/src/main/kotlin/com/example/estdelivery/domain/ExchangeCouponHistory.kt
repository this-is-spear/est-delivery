package com.example.estdelivery.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "exchange_coupon_history", catalog = "coupon")
class ExchangeCouponHistory(
    val expiredCouponId: Long,
    val rewardCouponId: Long,
    val jobExecutionId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
)
