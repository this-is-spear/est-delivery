package com.example.estdelivery.domain.member

import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.coupon.CouponBook

class UnusedCouponBook(
    private val unUsedCoupons: CouponBook = CouponBook(),
) {
    fun showUnusedCoupons(): List<Coupon> {
        return unUsedCoupons.showCoupons()
    }

    fun addUnusedCoupon(coupon: Coupon) {
        require(unUsedCoupons.showCoupons().contains(coupon).not()) { "이미 존재하는 쿠폰입니다." }
        unUsedCoupons.addCoupon(coupon)
    }

    fun removeUsedCoupon(coupon: Coupon) {
        require(unUsedCoupons.showCoupons().contains(coupon)) { "존재하지 않는 쿠폰입니다." }
        unUsedCoupons.deleteCoupon(coupon)
    }
}
