package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.coupon.domain.member.Member

interface UpdateRandomCouponIssueEventStatePort {
    fun participate(event: RandomCouponIssueEvent, participatedMember: Member)
}
