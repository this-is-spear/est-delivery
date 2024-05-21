package com.example.estdelivery.coupon.domain.coupon

import java.util.UUID

data class GiftCouponCode(
    val code: String,
) {
    init {
        require(code.isNotBlank()) { "쿠폰 선물 코드는 비어있을 수 없습니다." }
    }

    companion object {
        fun create(): GiftCouponCode {
            return GiftCouponCode(UUID.randomUUID().toString().replace("-", "").substring(0..9))
        }
    }
}
