package com.example.estdelivery.domain.fixture

import com.example.estdelivery.domain.event.DiscountAmountProbability
import com.example.estdelivery.domain.event.DiscountRange
import com.example.estdelivery.domain.event.EventDiscountType
import com.example.estdelivery.domain.event.ProbabilityRange
import com.example.estdelivery.domain.event.RandomCouponIssueEvent

fun 랜덤_비율_할인_이벤트(isProgress: Boolean = true) =
    RandomCouponIssueEvent(
        DiscountAmountProbability(
            listOf(
                ProbabilityRange(10, 20, 0.2),
                ProbabilityRange(21, 30, 0.3),
                ProbabilityRange(31, 40, 0.3),
                ProbabilityRange(41, 50, 0.2),
            ),
            DiscountRange(10, 50),
        ),
        1,
        isProgress,
        "비율 할인 쿠폰 이벤트 시작~",
        EventDiscountType.RATE,
    )

fun 랜덤_고정_할인_이벤트(isProgress: Boolean = true) =
    RandomCouponIssueEvent(
        DiscountAmountProbability(
            listOf(
                ProbabilityRange(1000, 1500, 0.2),
                ProbabilityRange(1600, 2000, 0.3),
                ProbabilityRange(2100, 2500, 0.3),
                ProbabilityRange(2600, 3000, 0.2),
            ),
            DiscountRange(1000, 3000),
        ),
        1,
        isProgress,
        "고정 할인 쿠폰 이벤트 시작~",
        EventDiscountType.FIXED,
    )
