package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.application.port.out.state.UpdateRandomCouponIssueEventState

interface UpdateRandomCouponIssueEventStatePort {
    fun update(event: UpdateRandomCouponIssueEventState)
}
