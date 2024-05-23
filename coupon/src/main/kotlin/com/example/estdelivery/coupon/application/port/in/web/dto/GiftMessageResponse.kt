package com.example.estdelivery.coupon.application.port.`in`.web.dto

import java.net.URL
import java.time.LocalDate

data class GiftMessageResponse(
    val senderName: String,
    val description: String,
    val enrollEndDate: LocalDate,
    val enrollHref: URL,
)
