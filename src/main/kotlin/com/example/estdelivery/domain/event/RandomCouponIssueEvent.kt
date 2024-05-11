package com.example.estdelivery.domain.event

import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.coupon.Coupon.FixDiscountCoupon
import com.example.estdelivery.domain.coupon.Coupon.RateDiscountCoupon
import com.example.estdelivery.domain.coupon.CouponType
import com.example.estdelivery.domain.event.EventDiscountType.FIXED
import com.example.estdelivery.domain.event.EventDiscountType.RATE
import com.example.estdelivery.domain.member.Member

class RandomCouponIssueEvent(
    discountAmountProbability: DiscountAmountProbability,
    val id: Long,
    private val description: String,
    private val discountType: EventDiscountType,
    private val discountAmount: () -> Int = {
        discountAmountProbability.getAmountBetween()
    },
    private val participatedMembers: List<Member> = emptyList(),
) {
    fun createCoupon(): Coupon {
        return when (discountType) {
            RATE -> RateDiscountCoupon(
                discountRate = discountAmount(),
                name = "${discountAmount()} 랜덤 비율 할인 쿠폰",
                description = description,
                couponType = CouponType.IS_PUBLISHED
            )

            FIXED -> FixDiscountCoupon(
                discountAmount = discountAmount(),
                name = "${discountAmount()} 랜덤 고정 할인 쿠폰",
                description = description,
                couponType = CouponType.IS_PUBLISHED
            )
        }
    }

    fun addParticipatedMember(member: Member) = participatedMembers + member
    fun alreadyParticipatedMember(member: Member) = participatedMembers.contains(member)
}
