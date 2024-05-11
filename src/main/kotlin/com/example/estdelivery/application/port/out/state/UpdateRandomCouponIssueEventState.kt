package com.example.estdelivery.application.port.out.state

import com.example.estdelivery.domain.event.RandomCouponIssueEvent

class UpdateRandomCouponIssueEventState(
    val id: Long,
    val participatedMembers: List<MemberState>,
) {
    companion object {
        fun from(event: RandomCouponIssueEvent): UpdateRandomCouponIssueEventState {
            return UpdateRandomCouponIssueEventState(
                event.id,
                participatedMembers = event.showParticipatedMembers().map { MemberState.from(it) }
            )
        }
    }
}
