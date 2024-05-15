package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook

class HandOutCouponBook(
    private val handOutCoupons: CouponBook = CouponBook(),
) {
    fun showHandOutCoupon(): List<Coupon> {
        return handOutCoupons.showCoupons()
    }

    fun addHandOutCoupon(coupon: Coupon) {
        require(coupon.isHandOut()) { "나눠줄 수 없는 쿠폰입니다." }
        if (handOutCoupons.showCoupons().contains(coupon)) {
            return
        }
        handOutCoupons.addCoupon(coupon)
    }

    override fun toString(): String {
        return "HandOutCouponBook(handOutCoupons=$handOutCoupons)"
    }
}
