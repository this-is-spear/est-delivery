package com.example.estdelivery.coupon.domain.shop

import com.example.estdelivery.coupon.domain.fixture.게시할_쿠폰
import com.example.estdelivery.coupon.domain.fixture.나눠줄_쿠폰
import com.example.estdelivery.coupon.domain.fixture.이벤트_쿠폰
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.member.UnusedCouponBook
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ShopOwnerTest : FreeSpec({
    val 프리퍼 =
        Shop(
            PublishedCouponBook(),
            PublishedEventCouponBook(),
            HandOutCouponBook(),
            UsedCouponBook(),
            RoyalCustomers(),
            1,
            "프리퍼",
        )

    "모든 회원에게 쿠폰을 나눠줄 수 있다." {
        // given
        val 단골_리스트 = RoyalCustomers()
        val 홍길동 = Member(1, "홍길동", UnusedCouponBook())
        val 김철수 = Member(2, "김철수", UnusedCouponBook())
        단골_리스트.addRoyalCustomers(홍길동, 김철수)

        val 프리퍼_가게_사장님 =
            ShopOwner(
                Shop(
                    PublishedCouponBook(),
                    PublishedEventCouponBook(),
                    HandOutCouponBook(),
                    UsedCouponBook(),
                    단골_리스트,
                    2,
                    "프리퍼",
                ),
                1
            )

        // when
        프리퍼_가게_사장님.handOutCouponToRoyalCustomersInShop(나눠줄_쿠폰)

        // then
        프리퍼_가게_사장님.showHandOutCouponInShop().contains(나눠줄_쿠폰) shouldBe true
    }

    "쿠폰을 가게에 게시한다." {
        // given
        val 가게_주인 = ShopOwner(프리퍼, 1)

        // when
        가게_주인.publishCouponInShop(게시할_쿠폰)

        // then
        가게_주인.showPublishedCouponsInShop().contains(게시할_쿠폰) shouldBe true
    }

    "단골 회원을 가게에 추가한다." {
        // given
        val 가게_주인 = ShopOwner(프리퍼, 2)
        val 홍길동 = Member(1, "홍길동", UnusedCouponBook())
        val 김철수 = Member(2, "김철수", UnusedCouponBook())

        // when
        가게_주인.addRoyalCustomersInShop(홍길동, 김철수)

        // then
        가게_주인.showRoyalCustomersInShop().contains(홍길동) shouldBe true
        가게_주인.showRoyalCustomersInShop().contains(김철수) shouldBe true
    }

    "이벤트 쿠폰을 가게에 발행한다." {
        // given
        val 가게_주인 = ShopOwner(프리퍼, 3)

        // when
        가게_주인.issueEventCouponInShop(이벤트_쿠폰)

        // then
        가게_주인.showShop().showEventCoupons().contains(이벤트_쿠폰) shouldBe true
    }
})
