package com.example.estdelivery.domain.shop

import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.coupon.CouponBook

class UsedCouponBook(
    private val usedCoupons: CouponBook = CouponBook(),
) {
    fun useCoupon(
        coupon: Coupon,
        shopCouponBook: CouponBook,
    ) {
        require(usedCoupons.showCoupons().contains(coupon).not()) { "이미 사용한 쿠폰입니다." }
        require(shopCouponBook.showCoupons().contains(coupon)) { "게시하거나 나눠주지 않았거나 이벤트를 진행하지 않은 쿠폰입니다." }
        usedCoupons.addCoupon(coupon)
    }

    fun showUsedCoupons(): List<Coupon> {
        return usedCoupons.showCoupons()
    }
}
