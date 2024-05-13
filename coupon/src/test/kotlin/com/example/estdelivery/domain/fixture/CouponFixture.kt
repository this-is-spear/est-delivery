package com.example.estdelivery.domain.fixture

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.CouponType

val 나눠줄_쿠폰 = Coupon.RateDiscountCoupon(10, "10% 할인 쿠폰", "10% 할인 쿠폰 설명", CouponType.IS_HAND_OUT)
val 나눠준_비율_할인_쿠폰 = Coupon.RateDiscountCoupon(10, "10% 할인 쿠폰", "10% 할인 쿠폰 설명", CouponType.IS_HAND_OUT, 1L)
val 게시할_쿠폰 = Coupon.FixDiscountCoupon(1000, "1000원 할인 쿠폰", "1000원 할인 쿠폰 설명", CouponType.IS_PUBLISHED)
val 게시된_고정_할인_쿠폰 = Coupon.FixDiscountCoupon(1000, "1000원 할인 쿠폰", "1000원 할인 쿠폰 설명", CouponType.IS_PUBLISHED, 2L)
val 게시되지_않은_쿠폰 = Coupon.FixDiscountCoupon(1000, "1000원 할인 쿠폰", "1000원 할인 쿠폰 설명", CouponType.IS_HAND_OUT, 1L)
val 나눠주지_않은_쿠폰 = Coupon.FixDiscountCoupon(1000, "1000원 할인 쿠폰", "1000원 할인 쿠폰 설명", CouponType.IS_PUBLISHED, 2L)
val 이벤트하지_않은_쿠폰 = Coupon.FixDiscountCoupon(1000, "1000원 할인 쿠폰", "1000원 할인 쿠폰 설명", CouponType.IS_EVENT, 10L)
val 이벤트_쿠폰 = Coupon.RateDiscountCoupon(13, "13% 이벤트 쿠폰", "13% 할인 쿠폰 설명", CouponType.IS_PUBLISHED, 3L)
