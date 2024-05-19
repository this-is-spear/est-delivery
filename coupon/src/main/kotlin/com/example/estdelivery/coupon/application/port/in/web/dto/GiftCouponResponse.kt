package com.example.estdelivery.coupon.application.port.`in`.web.dto

import com.example.estdelivery.coupon.domain.coupon.CouponType

data class GiftCouponResponse(
    val id: Long,
    val name: String,
    val discountAmount: Int,
    val discountType: CouponType
)
