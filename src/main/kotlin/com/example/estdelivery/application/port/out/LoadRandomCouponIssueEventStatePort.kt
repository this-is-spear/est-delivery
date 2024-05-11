package com.example.estdelivery.application.port.out

import com.example.estdelivery.application.port.out.state.RandomCouponIssueEventState

interface LoadRandomCouponIssueEventStatePort {
    fun findById(eventId: Long): RandomCouponIssueEventState
}
