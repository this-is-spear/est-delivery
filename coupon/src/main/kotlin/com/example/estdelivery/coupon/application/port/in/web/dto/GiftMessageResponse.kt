package com.example.estdelivery.coupon.application.port.`in`.web.dto

import java.net.URL

data class GiftMessageResponse(
    val senderName: String,
    val description: String,
    val enrollHref: URL,
)
