package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.event.RandomCouponIssueEvent

interface LoadRandomCouponIssueEventStatePort {
    fun findEvent(eventId: Long): RandomCouponIssueEvent
}
