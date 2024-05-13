package com.example.estdelivery.coupon.domain.event

import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.fixture.랜덤_고정_할인_이벤트
import com.example.estdelivery.coupon.domain.fixture.랜덤_비율_할인_이벤트
import com.example.estdelivery.coupon.domain.fixture.이건창
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.ints.shouldBeInRange

class RandomCouponIssueEventTest : FreeSpec({
    "이벤트로 고정 할인 쿠폰을 발급한다." {
        val coupon = 랜덤_고정_할인_이벤트().participateCreateCouponEvent(이건창()) as Coupon.FixDiscountCoupon
        coupon.discountAmount shouldBeInRange 1000..3000
    }

    "이벤트로 비율 할인 쿠폰을 발급한다." {
        val coupon = 랜덤_비율_할인_이벤트().participateCreateCouponEvent(이건창()) as Coupon.RateDiscountCoupon
        coupon.discountRate shouldBeInRange 10..50
    }

    "진행중인 이벤트여야 한다." {
        shouldThrow<IllegalArgumentException> {
            랜덤_고정_할인_이벤트(isProgress = false)
        }
    }

    "이미 발급한 사용자는 발급할 수 없다." {
        val 랜덤비율할인이벤트 = 랜덤_비율_할인_이벤트()
        val 동일한_사용자 = 이건창()

        랜덤비율할인이벤트.participateCreateCouponEvent(동일한_사용자)

        shouldThrow<IllegalArgumentException> {
            랜덤비율할인이벤트.participateCreateCouponEvent(동일한_사용자)
        }
    }
})
