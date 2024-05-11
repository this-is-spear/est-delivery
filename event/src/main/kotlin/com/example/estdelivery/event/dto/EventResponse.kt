package com.example.estdelivery.event.dto

import com.example.estdelivery.event.entity.EventDiscountType
import com.example.estdelivery.event.entity.ProbabilityRange

data class EventResponse(
    val id: Long,
    val description: String,
    val isProgress: Boolean,
    val discountType: EventDiscountType,
    val intervalsProbability: List<ProbabilityRange>,
    val discountRangeMin: Int,
    val discountRangeMax: Int,
    val participatedMembers: List<Long>,
)
