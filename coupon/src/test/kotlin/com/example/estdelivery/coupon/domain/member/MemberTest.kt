package com.example.estdelivery.coupon.domain.member

import com.example.estdelivery.coupon.domain.fixture.게시된_고정_할인_쿠폰
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class MemberTest : FreeSpec({
    lateinit var member: Member

    beforeTest {
        member = Member(1, "홍길동", UnusedCouponBook())
    }

    "쿠폰을 추가할 수 있다." {
        member.receiveCoupon(게시된_고정_할인_쿠폰)
        member.showMyCouponBook().contains(게시된_고정_할인_쿠폰) shouldBe true
    }

    "쿠폰을 사용할 수 있다." {
        member.receiveCoupon(게시된_고정_할인_쿠폰)
        member.useCoupon(게시된_고정_할인_쿠폰)
        member.showMyCouponBook().contains(게시된_고정_할인_쿠폰) shouldBe false
    }

    "쿠폰을 선물한다." {
        val target = Member(2, "김철수", UnusedCouponBook())
        member.receiveCoupon(게시된_고정_할인_쿠폰)
        member.sendCoupon(게시된_고정_할인_쿠폰, target)
        member.showMyCouponBook().contains(게시된_고정_할인_쿠폰) shouldBe false
        target.showMyCouponBook().contains(게시된_고정_할인_쿠폰) shouldBe true
    }
})
