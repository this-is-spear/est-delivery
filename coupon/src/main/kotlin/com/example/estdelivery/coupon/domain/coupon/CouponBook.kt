package com.example.estdelivery.coupon.domain.coupon

class CouponBook(
    private var coupons: List<Coupon> = listOf(),
) {
    fun showCoupons(): List<Coupon> {
        return coupons.toList()
    }

    fun removeCoupon(coupon: Coupon) {
        require(coupons.contains(coupon)) { "존재하지 않는 쿠폰입니다." }
        coupons = coupons - coupon
    }

    fun addCoupon(coupon: Coupon) {
        require(coupons.contains(coupon).not()) { "이미 존재하는 쿠폰입니다." }
        coupons = coupons + coupon
    }

    override fun toString(): String {
        return "CouponBook(coupons=$coupons)"
    }
}
