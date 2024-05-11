package com.example.estdelivery.application.port.out

import com.example.estdelivery.application.port.out.state.UpdateRandomCouponIssueEventState

interface UpdateRandomCouponIssueEventStatePort {
    fun update(event: UpdateRandomCouponIssueEventState)
}
