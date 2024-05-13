package com.example.estdelivery.coupon.application.port.`in`.command

import com.example.estdelivery.coupon.domain.coupon.Coupon

data class PublishCouponCommand(
    val shopOwnerId: Long,
    val shopId: Long,
    val coupon: Coupon,
)
