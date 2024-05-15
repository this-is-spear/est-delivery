package com.example.estdelivery.coupon.domain.event

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.property.checkAll

class DiscountAmountProbabilityTest : FreeSpec({

    "할인 금액을 선정할 DiscountAmountProbability를 생성한다." {
        shouldNotThrow<IllegalArgumentException> {
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(10, 15, 0.2),
                    ProbabilityRange(16, 20, 0.3),
                    ProbabilityRange(21, 25, 0.3),
                    ProbabilityRange(26, 30, 0.2),
                ),
                DiscountRange(10, 30),
            )
        }
    }

    "확률의 합이 1이 아니면 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> {
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(10, 15, 0.2),
                    ProbabilityRange(16, 20, 0.3),
                    ProbabilityRange(21, 25, 0.3),
                    ProbabilityRange(26, 30, 0.1),
                ),
                DiscountRange(10, 30),
            )
        }
    }

    "DiscountRange의 최소값보다 작은 확률이 존재하면 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> {
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(5, 15, 0.2),
                    ProbabilityRange(16, 20, 0.3),
                    ProbabilityRange(21, 25, 0.3),
                    ProbabilityRange(26, 30, 0.2),
                ),
                DiscountRange(10, 30),
            )
        }
    }

    "DiscountRange의 최대값보다 큰 확률이 존재하면 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> {
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(10, 15, 0.2),
                    ProbabilityRange(16, 20, 0.3),
                    ProbabilityRange(21, 25, 0.3),
                    ProbabilityRange(26, 35, 0.2),
                ),
                DiscountRange(10, 30),
            )
        }
    }

    "할인 금액을 선정한다." {
        val discountAmountProbability =
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(10, 15, 0.2),
                    ProbabilityRange(16, 20, 0.3),
                    ProbabilityRange(21, 25, 0.3),
                    ProbabilityRange(26, 30, 0.2),
                ),
                DiscountRange(10, 30),
            )
        checkAll<Int> {
            val discountAmount = discountAmountProbability.getAmountBetween()

            collect(discountAmount)
            when (discountAmount) {
                in 10..15 -> collect("statistic", "10 ~ 15")
                in 16..20 -> collect("statistic", "16 ~ 20")
                in 21..25 -> collect("statistic", "21 ~ 25")
                in 26..30 -> collect("statistic", "26 ~ 30")
            }

            discountAmount shouldBeInRange 10..30
        }
    }
})
