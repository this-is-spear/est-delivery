package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.application.port.out.state.LoadRandomCouponIssueEventState

interface LoadRandomCouponIssueEventStatePort {
    fun findById(eventId: Long): LoadRandomCouponIssueEventState
}
