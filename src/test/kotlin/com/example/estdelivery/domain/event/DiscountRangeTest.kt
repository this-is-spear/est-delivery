package com.example.estdelivery.domain.event

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec

class DiscountRangeTest : FreeSpec({
    "min 은 max보다 작아야 합니다." {
        val min = 10
        val max = 5

        shouldThrow<IllegalArgumentException> {
            DiscountRange(min, max)
        }
    }

    "mix 과 max는 같을 수 없습니다." {
        val min = 10
        val max = 10

        shouldThrow<IllegalArgumentException> {
            DiscountRange(min, max)
        }
    }

    "min은 0보다 작을 수 없습니다." {
        val min = -1
        val max = 10

        shouldThrow<IllegalArgumentException> {
            DiscountRange(min, max)
        }
    }
})
