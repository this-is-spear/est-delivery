package com.example.estdelivery.application.port.`in`.command

data class IssueEventCouponCommand(
    val eventId: Long,
    val shopId: Long,
    val memberId: Long,
)
