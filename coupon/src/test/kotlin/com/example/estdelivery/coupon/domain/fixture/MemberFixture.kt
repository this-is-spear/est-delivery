package com.example.estdelivery.coupon.domain.fixture

import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook

fun 일건창() = Member(1L, "일건창", UnusedCouponBook())

fun 이건창() = Member(2L, "이건창", UnusedCouponBook())

fun 나눠준_쿠폰을_가진_삼건창() = Member(3L, "이건창", UnusedCouponBook(CouponBook(listOf(나눠준_비율_할인_쿠폰))))
