package com.example.estdelivery.application.port.out.state

import com.example.estdelivery.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.domain.member.Member

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
