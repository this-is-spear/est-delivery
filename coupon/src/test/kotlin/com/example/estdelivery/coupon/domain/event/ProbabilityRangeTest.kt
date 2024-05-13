package com.example.estdelivery.coupon.domain.event

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec

class ProbabilityRangeTest : FreeSpec({

    "min은 max보다 작아야 합니다." {
        val min = 10
        val max = 5

        shouldThrow<IllegalArgumentException> {
            ProbabilityRange(min, max, 0.1)
        }
    }

    "min과 max는 같을 수 없습니다." {
        val min = 10
        val max = 10

        shouldThrow<IllegalArgumentException> {
            ProbabilityRange(min, max, 0.1)
        }
    }

    "min은 0보다 작을 수 없습니다." {
        val min = -1
        val max = 10

        shouldThrow<IllegalArgumentException> {
            ProbabilityRange(min, max, 0.1)
        }
    }

    "probability는 0일 수 없습니다." {
        val min = 0
        val max = 10
        val probability = 0.0

        shouldThrow<IllegalArgumentException> {
            ProbabilityRange(min, max, probability)
        }
    }

    "probability는 1일 수 없습니다." {
        val min = 0
        val max = 10
        val probability = 1.0

        shouldThrow<IllegalArgumentException> {
            ProbabilityRange(min, max, probability)
        }
    }
})
