package com.example.estdelivery.application.port.out

import com.example.estdelivery.application.port.out.state.LoadRandomCouponIssueEventState

interface LoadRandomCouponIssueEventStatePort {
    fun findById(eventId: Long): LoadRandomCouponIssueEventState
}
