package com.example.estdelivery.domain.shop

import com.example.estdelivery.domain.fixture.이벤트_쿠폰
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class PublishedEventCouponBookTest : FreeSpec({

    "이벤트 쿠폰을 발급한다." {
        // given
        val publishedEventCouponBook = PublishedEventCouponBook()
        val eventCoupon = 이벤트_쿠폰

        // when
        val issuedEventCoupon = publishedEventCouponBook.issueEventCoupon(eventCoupon)

        // then
        publishedEventCouponBook.showEventCoupons().contains(issuedEventCoupon) shouldBe true
    }

    "이미 발급한 이벤트 쿠폰은 재발급 할 수 없다." {
        // given
        val publishedEventCouponBook = PublishedEventCouponBook()
        val eventCoupon = 이벤트_쿠폰

        // when
        publishedEventCouponBook.issueEventCoupon(eventCoupon)

        // then
        shouldThrow<IllegalArgumentException> {
            publishedEventCouponBook.issueEventCoupon(eventCoupon)
        }
    }
})
