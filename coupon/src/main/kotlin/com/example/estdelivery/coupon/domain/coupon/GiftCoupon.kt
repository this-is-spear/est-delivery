package com.example.estdelivery.coupon.domain.coupon

import java.time.LocalDate

/**
 * 선물하는 쿠폰을 선정할 때 여러 제약이 생길 수 있다.
 * 제약 변경에 대응하기 위해 컴포지션을 활용한다.
 */
data class GiftCoupon(
    val coupon: Coupon,
    val enrollEndDate: LocalDate,
    val isUsed: Boolean = false,
) {
    init {
        require(!coupon.isPublished()) { "발행한 쿠폰은 선물할 수 없습니다." }
        require(enrollEndDate.isAfter(LocalDate.now())) { "유효기간이 지난 쿠폰은 선물할 수 없습니다." }
    }
}
