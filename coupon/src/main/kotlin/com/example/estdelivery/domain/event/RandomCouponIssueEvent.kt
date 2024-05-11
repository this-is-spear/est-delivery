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
    val isProgress: Boolean,
    val description: String,
    val discountType: EventDiscountType,
    private val discountAmount: () -> Int = {
        discountAmountProbability.getAmountBetween()
    },
    private var participatedMembers: List<Member> = emptyList(),
) {
    init {
        require(isProgress) { "진행 중인 이벤트가 아닙니다." }
    }

    fun participateCreateCouponEvent(member: Member): Coupon {
        require(ensureParticipatedMember(member)) { "이미 참여한 사용자입니다." }
        addParticipatedMember(member)

        return when (discountType) {
            RATE ->
                RateDiscountCoupon(
                    discountRate = discountAmount(),
                    name = "${discountAmount()} 랜덤 비율 할인 쿠폰",
                    description = description,
                    couponType = CouponType.IS_PUBLISHED,
                )

            FIXED ->
                FixDiscountCoupon(
                    discountAmount = discountAmount(),
                    name = "${discountAmount()} 랜덤 고정 할인 쿠폰",
                    description = description,
                    couponType = CouponType.IS_PUBLISHED,
                )
        }
    }

    fun showParticipatedMembers(): List<Member> = participatedMembers

    private fun addParticipatedMember(member: Member) {
        participatedMembers = participatedMembers + member
    }

    private fun ensureParticipatedMember(member: Member) = !participatedMembers.contains(member)
}
