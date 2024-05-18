package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.domain.coupon.GiftCoupon

interface FindAvailableGiftCouponUseCase {
    /**
     * 회원은 가게에 발행된 쿠폰 중 선물 가능한 쿠폰을 조회한다.
     * @param memberId
     */
    fun findAvailableGiftCoupon(memberId: Long): List<GiftCoupon>
}
