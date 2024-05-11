package com.example.estdelivery.domain.fixture

import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.coupon.CouponBook
import com.example.estdelivery.domain.member.Member
import com.example.estdelivery.domain.shop.HandOutCouponBook
import com.example.estdelivery.domain.shop.PublishedCouponBook
import com.example.estdelivery.domain.shop.PublishedEventCouponBook
import com.example.estdelivery.domain.shop.RoyalCustomers
import com.example.estdelivery.domain.shop.Shop
import com.example.estdelivery.domain.shop.UsedCouponBook

const val 가게_이름 = "프리퍼"

fun 새로_창업해서_아무것도_없는_프리퍼() =
    Shop(
        PublishedCouponBook(CouponBook(listOf())),
        PublishedEventCouponBook(),
        HandOutCouponBook(CouponBook(listOf())),
        UsedCouponBook(CouponBook(listOf())),
        RoyalCustomers(listOf()),
        가게_이름,
        1L,
    )

fun 나눠준_쿠폰이_있는_프리퍼(vararg coupon: Coupon) =
    Shop(
        PublishedCouponBook(CouponBook(listOf())),
        PublishedEventCouponBook(),
        HandOutCouponBook(CouponBook(listOf(*coupon))),
        UsedCouponBook(CouponBook(listOf())),
        RoyalCustomers(listOf(나눠준_쿠폰을_가진_삼건창(), 일건창(), 이건창())),
        가게_이름,
        1L,
    )

fun 게시된_쿠폰이_있는_프리퍼(vararg coupon: Coupon) =
    Shop(
        PublishedCouponBook(CouponBook(listOf(*coupon))),
        PublishedEventCouponBook(),
        HandOutCouponBook(CouponBook(listOf())),
        UsedCouponBook(CouponBook(listOf())),
        RoyalCustomers(listOf()),
        "프리퍼",
        1L,
    )

fun 단골이_있는_멋진_프리퍼(vararg member: Member) =
    Shop(
        PublishedCouponBook(CouponBook(listOf())),
        PublishedEventCouponBook(),
        HandOutCouponBook(CouponBook(listOf())),
        UsedCouponBook(CouponBook(listOf())),
        RoyalCustomers(listOf(*member)),
        "프리퍼",
        1L,
    )