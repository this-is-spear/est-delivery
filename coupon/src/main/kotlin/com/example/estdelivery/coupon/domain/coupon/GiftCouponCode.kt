package com.example.estdelivery.coupon.domain.coupon

data class GiftCouponCode(
    val code: String,
) {
    init {
        require(code.isNotBlank()) { "쿠폰 선물 코드는 비어있을 수 없습니다." }
    }
}
