package com.example.estdelivery.coupon.application.port.`in`.command

import com.example.estdelivery.coupon.domain.coupon.Coupon

class HandoutCouponCommand(
    val shopOwnerId: Long,
    val shopId: Long,
    val coupon: Coupon,
)
