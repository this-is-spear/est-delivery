package com.example.estdelivery.coupon.application.port.out.state

import com.example.estdelivery.coupon.domain.event.DiscountAmountProbability
import com.example.estdelivery.coupon.domain.event.EventDiscountType
import com.example.estdelivery.coupon.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.coupon.domain.member.Member

class LoadRandomCouponIssueEventState(
    private val id: Long,
    private val description: String,
    private val isProgress: Boolean,
    private val disCountType: EventDiscountType,
    private val probabilityRanges: DiscountAmountProbability,
    private val participatedMembers: List<Member>,
) {
    fun toEvent(): RandomCouponIssueEvent {
        return RandomCouponIssueEvent(
            id = id,
            description = description,
            isProgress = isProgress,
            discountType = disCountType,
            discountAmountProbability = probabilityRanges,
            participatedMembers = participatedMembers,
        )
    }
}
