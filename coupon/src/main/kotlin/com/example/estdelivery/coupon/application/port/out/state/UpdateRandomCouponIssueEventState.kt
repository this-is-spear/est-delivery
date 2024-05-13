package com.example.estdelivery.coupon.application.port.out.state

import com.example.estdelivery.coupon.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.coupon.domain.member.Member

class UpdateRandomCouponIssueEventState(
    val id: Long,
    val participatedMembers: List<Member>,
) {
    companion object {
        fun from(event: RandomCouponIssueEvent): UpdateRandomCouponIssueEventState {
            return UpdateRandomCouponIssueEventState(
                event.id,
                participatedMembers = event.showParticipatedMembers(),
            )
        }
    }
}
