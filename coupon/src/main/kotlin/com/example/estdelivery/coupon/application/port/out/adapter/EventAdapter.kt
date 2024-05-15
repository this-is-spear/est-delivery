package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.LoadRandomCouponIssueEventStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateRandomCouponIssueEventStatePort
import com.example.estdelivery.coupon.application.port.out.adapter.infra.EventInfraAdapter
import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.UpdateParticipatedMembersState
import com.example.estdelivery.coupon.domain.event.DiscountAmountProbability
import com.example.estdelivery.coupon.domain.event.DiscountRange
import com.example.estdelivery.coupon.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.coupon.domain.member.Member
import org.springframework.stereotype.Component

@Component
class EventAdapter(
    private val eventInfraAdapter: EventInfraAdapter,
    private val memberAdapter: MemberAdapter,
) : LoadRandomCouponIssueEventStatePort, UpdateRandomCouponIssueEventStatePort {
    override fun findEvent(eventId: Long) = eventInfraAdapter.findEvent(eventId).let { eventState ->
        RandomCouponIssueEvent(
            id = eventState.id,
            description = eventState.description,
            isProgress = eventState.isProgress,
            discountType = eventState.discountType,
            discountAmountProbability = DiscountAmountProbability(
                intervalsProbability = eventState.intervalsProbability,
                discountRange = DiscountRange(eventState.discountRangeMin, eventState.discountRangeMax)
            ),
            participatedMembers = eventState.participatedMembers.map {
                memberAdapter.findMember(it)
            },
        )
    }

    override fun participate(event: RandomCouponIssueEvent, participatedMember: Member) {
        eventInfraAdapter.participateMember(
            UpdateParticipatedMembersState(
                id = event.id,
                participatedMemberId = participatedMember.id
            )
        )
    }
}
