package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponBook

class PublishedEventCouponBook(
    private val publishedEventCoupons: CouponBook = CouponBook(),
) {
    fun issueEventCoupon(coupon: Coupon): Coupon {
        require(publishedEventCoupons.showCoupons().contains(coupon).not()) { "이미 등록된 쿠폰입니다." }
        publishedEventCoupons.addCoupon(coupon)
        return publishedEventCoupons.showCoupons().find { it == coupon }!!
    }

    fun showEventCoupons() = publishedEventCoupons.showCoupons()
}
