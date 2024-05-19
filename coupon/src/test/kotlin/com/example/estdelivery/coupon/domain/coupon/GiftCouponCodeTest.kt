package com.example.estdelivery.coupon.domain.coupon

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class GiftCouponCodeTest : FreeSpec({
    "쿠폰 선물 코드는 비어있을 수 없다." {
        val code = ""
        val exception = shouldThrow<IllegalArgumentException> {
            GiftCouponCode(code)
        }
        exception.message shouldBe "쿠폰 선물 코드는 비어있을 수 없습니다."
    }
})
