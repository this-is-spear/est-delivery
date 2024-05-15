package com.example.estdelivery.coupon.application.port.out.adapter.infra.dto

import com.example.estdelivery.coupon.domain.event.EventDiscountType
import com.example.estdelivery.coupon.domain.event.ProbabilityRange

class EventState(
    val id: Long,
    val description: String,
    val isProgress: Boolean,
    val discountType: EventDiscountType,
    val intervalsProbability: List<ProbabilityRange>,
    val discountRangeMin: Int,
    val discountRangeMax: Int,
    val participatedMembers: List<Long>,
)