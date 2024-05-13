package com.example.estdelivery.coupon.application.port.`in`.command

data class GiftCouponCommand(
    val couponId: Long,
    val senderId: Long,
    val receiverId: Long,
)
