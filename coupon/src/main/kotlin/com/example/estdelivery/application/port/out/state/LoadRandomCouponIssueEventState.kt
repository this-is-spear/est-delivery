package com.example.estdelivery.application.port.out.state

import com.example.estdelivery.domain.event.DiscountAmountProbability
import com.example.estdelivery.domain.event.EventDiscountType
import com.example.estdelivery.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.domain.member.Member

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
