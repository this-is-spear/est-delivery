package com.example.estdelivery.domain.event

import com.example.estdelivery.domain.coupon.Coupon
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeInRange

class RandomCouponIssueEventTest : FreeSpec({

    "이벤트로 고정 할인 쿠폰을 발급한다." {
        val event = RandomCouponIssueEvent(
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(1000, 1500, 0.2),
                    ProbabilityRange(1600, 2000, 0.3),
                    ProbabilityRange(2100, 2500, 0.3),
                    ProbabilityRange(2600, 3000, 0.2),
                ),
                DiscountRange(1000, 3000)
            ),
            1,
            "고정 할인 쿠폰 이벤트 시작~",
            EventDiscountType.FIXED
        )

        val coupon = event.createCoupon() as Coupon.FixDiscountCoupon
        coupon.discountAmount shouldBeInRange 1000..3000
    }

    "이벤트로 비율 할인 쿠폰을 발급한다." {
        val event = RandomCouponIssueEvent(
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(10, 20, 0.2),
                    ProbabilityRange(21, 30, 0.3),
                    ProbabilityRange(31, 40, 0.3),
                    ProbabilityRange(41, 50, 0.2),
                ),
                DiscountRange(10, 50)
            ),
            1,
            "비율 할인 쿠폰 이벤트 시작~",
            EventDiscountType.RATE
        )

        val coupon = event.createCoupon() as Coupon.RateDiscountCoupon
        coupon.discountRate shouldBeInRange 10..50
    }
})
