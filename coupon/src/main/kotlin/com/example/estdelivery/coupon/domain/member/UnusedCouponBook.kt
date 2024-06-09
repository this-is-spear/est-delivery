package com.example.estdelivery.coupon.domain.member

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook

class UnusedCouponBook(
    private val coupons: CouponBook = CouponBook(),
) {
    fun showUnusedCoupons(): List<Coupon> {
        return coupons.showCoupons()
    }

    fun addUnusedCoupon(coupon: Coupon) {
        require(coupons.showCoupons().contains(coupon).not()) { "이미 존재하는 쿠폰입니다." }
        coupons.addCoupon(coupon)
    }

    fun useCoupon(coupon: Coupon) {
        require(coupons.showCoupons().contains(coupon)) { "존재하지 않는 쿠폰입니다." }
        coupons.removeCoupon(coupon)
        coupons.addCoupon(Coupon.UsedCoupon(coupon))
    }

    override fun toString(): String {
        return "UnusedCouponBook(unUsedCoupons=$coupons)"
    }
}
