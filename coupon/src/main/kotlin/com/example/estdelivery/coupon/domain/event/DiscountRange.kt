package com.example.estdelivery.coupon.domain.event

class DiscountRange(
    val min: Int,
    val max: Int,
) {
    init {
        require(min < max) { "min 은 max보다 작아야 합니다." }
        require(min != max) { "mix 과 max는 같을 수 없습니다." }
        require(min >= 0) { "min은 0보다 작을 수 없습니다." }
    }
}
