package com.example.estdelivery.coupon.domain.coupon

import com.example.estdelivery.coupon.domain.member.Member

data class GiftMessage(
    val sender: Member,
    val giftMessage: String,
    val giftCouponCode: GiftCouponCode,
    val giftCoupon: GiftCoupon
)
