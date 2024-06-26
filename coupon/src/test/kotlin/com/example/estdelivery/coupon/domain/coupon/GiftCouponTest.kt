package com.example.estdelivery.coupon.domain.coupon

import com.example.estdelivery.coupon.domain.fixture.게시된_고정_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.이벤트_쿠폰
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import java.time.LocalDate

class GiftCouponTest : FreeSpec({
    val tomorrow = LocalDate.now().plusDays(1)
    "나눠준 쿠폰은 선물할 수 있다." {
        shouldNotThrow<IllegalArgumentException> {
            GiftCoupon(나눠준_비율_할인_쿠폰, tomorrow)
        }
    }

    "이벤트 쿠폰은 선물할 수 있다." {
        shouldNotThrow<IllegalArgumentException> {
            GiftCoupon(이벤트_쿠폰, tomorrow)
        }
    }

    "발행한 쿠폰은 선물할 수 없다." {
        shouldThrow<IllegalArgumentException> {
            GiftCoupon(게시된_고정_할인_쿠폰, tomorrow)
        }
    }

    "유효기간이 지난 쿠폰을 사용 할 수 없다." {
        shouldThrow<IllegalArgumentException> {
            GiftCoupon(나눠준_비율_할인_쿠폰, LocalDate.now().minusDays(1))
        }
    }
})
