package com.example.estdelivery.coupon.domain.coupon

import com.example.estdelivery.coupon.domain.member.Member

data class GiftMessage(
    val sender: Member,
    val giftMessage: String,
    val giftCode: String,
    val giftCoupon: GiftCoupon
) {
    init {
        require(giftCode.isNotBlank()) { "선물 코드는 필수입니다." }
    }
}
