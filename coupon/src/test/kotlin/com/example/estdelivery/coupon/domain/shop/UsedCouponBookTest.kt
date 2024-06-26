package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.fixture.게시되지_않은_쿠폰
import com.example.estdelivery.coupon.domain.fixture.게시된_고정_할인_쿠폰
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class UsedCouponBookTest : FreeSpec({
    "이미 사용한 쿠폰은 재사용 할 수 없다." {
        // given
        val usedCouponBook = UsedCouponBook()
        val shopCouponBook = CouponBook()

        // when
        shopCouponBook.addCoupon(게시된_고정_할인_쿠폰)
        usedCouponBook.useCoupon(게시된_고정_할인_쿠폰, shopCouponBook)

        // then
        shouldThrow<IllegalArgumentException> { usedCouponBook.useCoupon(게시된_고정_할인_쿠폰, shopCouponBook) }
            .message shouldBe "이미 사용한 쿠폰입니다."
    }

    "존재하지 않는 쿠폰인 경우 사용 할 수 없다." {
        // given
        val usedCouponBook = UsedCouponBook()
        val shopCouponBook = CouponBook()

        // then
        shouldThrow<IllegalArgumentException> { usedCouponBook.useCoupon(게시되지_않은_쿠폰, shopCouponBook) }
            .message shouldBe "게시하거나 나눠주지 않았거나 이벤트를 진행하지 않은 쿠폰입니다."
    }
})
