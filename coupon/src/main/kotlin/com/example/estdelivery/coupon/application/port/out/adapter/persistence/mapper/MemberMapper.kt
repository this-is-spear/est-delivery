package com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberCouponEntity
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook

internal fun fromCouponMember(member: Member): MemberCouponEntity {
    return MemberCouponEntity(
        member.showMyCouponBook()
            .map { fromCoupon(it) },
        member.id,
    )
}

internal fun toMember(entity: MemberCouponEntity): Member {
    return Member(
        entity.id!!,
        "",
        UnusedCouponBook(
            CouponBook(entity.unusedCoupons.map {
                toCoupon(
                    it
                )
            }),
        ),
    )
}
