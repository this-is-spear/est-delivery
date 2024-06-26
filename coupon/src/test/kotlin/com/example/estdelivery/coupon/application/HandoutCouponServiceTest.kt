package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.command.HandoutCouponCommand
import com.example.estdelivery.coupon.application.port.out.CreateCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.나눠준_쿠폰을_가진_삼건창
import com.example.estdelivery.coupon.domain.fixture.나눠줄_쿠폰
import com.example.estdelivery.coupon.domain.fixture.단골이_있는_멋진_프리퍼
import com.example.estdelivery.coupon.domain.fixture.이건창
import com.example.estdelivery.coupon.domain.fixture.일건창
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class HandoutCouponServiceTest : FreeSpec({
    val loadShopOwnerStatePort = mockk<LoadShopOwnerStatePort>()
    val loadCouponStatePort = mockk<LoadCouponStatePort>()
    val updateShopOwnerStatePort = mockk<UpdateShopOwnerStatePort>()
    val createCouponStatePort = mockk<CreateCouponStatePort>()
    val transactionArea = TransactionArea()

    lateinit var handoutCouponService: HandoutCouponService

    beforeTest {
        handoutCouponService =
            HandoutCouponService(
                loadShopOwnerStatePort,
                loadCouponStatePort,
                updateShopOwnerStatePort,
                createCouponStatePort,
                transactionArea,
            )
    }

    "쿠폰을 나눠줄 수 있다." {
        // given
        val 가게 = 단골이_있는_멋진_프리퍼(일건창(), 이건창())
        val 프리퍼_주인 = ShopOwner(가게, 1L)
        val 변경된_프리퍼_주인_상태 = slot<ShopOwner>()

        val shopId = 가게.id
        val shopOwnerId = 프리퍼_주인.id

        val handoutCouponCommand = HandoutCouponCommand(
            shopOwnerId,
            shopId,
            나눠줄_쿠폰
        )
        every { loadShopOwnerStatePort.findById(shopOwnerId) } returns 프리퍼_주인
        every { loadCouponStatePort.exists(any()) } returns false
        every { createCouponStatePort.create(나눠줄_쿠폰) } returns 나눠준_비율_할인_쿠폰
        every { loadCouponStatePort.findById(any()) } returns 나눠준_비율_할인_쿠폰
        every { updateShopOwnerStatePort.updateShopOwnersCoupons(capture(변경된_프리퍼_주인_상태)) } returns Unit

        // when
        handoutCouponService.handoutCoupon(handoutCouponCommand)

        // then
        val 가게_주인 = 변경된_프리퍼_주인_상태.captured
        가게_주인.showHandOutCouponInShop() shouldContain 나눠준_비율_할인_쿠폰
        가게_주인.showRoyalCustomersInShop().map { member ->
            member.showMyCouponBook() shouldContain 나눠준_비율_할인_쿠폰
        }
    }

    "이미 나눠줬던 쿠폰을 다시 나눠줄 때 새로운 단골들에게만 나눠준다." {
        // given
        val 가게 = 단골이_있는_멋진_프리퍼(일건창(), 이건창(), 나눠준_쿠폰을_가진_삼건창())
        val 프리퍼_주인_상태 = ShopOwner(가게, 1L)
        val 변경된_프리퍼_주인_상태 = slot<ShopOwner>()

        val shopId = 가게.id
        val shopOwnerId = 프리퍼_주인_상태.id
        val handoutCouponCommand = HandoutCouponCommand(
            shopOwnerId,
            shopId,
            나눠줄_쿠폰
        )
        every { loadShopOwnerStatePort.findById(shopOwnerId) } returns 프리퍼_주인_상태
        every { loadCouponStatePort.exists(any()) } returns false
        every { createCouponStatePort.create(나눠줄_쿠폰) } returns 나눠준_비율_할인_쿠폰
        every { loadCouponStatePort.findById(any()) } returns 나눠준_비율_할인_쿠폰
        every { updateShopOwnerStatePort.updateShopOwnersCoupons(capture(변경된_프리퍼_주인_상태)) } returns Unit

        // when
        handoutCouponService.handoutCoupon(handoutCouponCommand)

        // then
        val 가게_주인 = 변경된_프리퍼_주인_상태.captured
        가게_주인.showHandOutCouponInShop() shouldContain 나눠준_비율_할인_쿠폰
        가게_주인.showRoyalCustomersInShop().map { member ->
            member.showMyCouponBook() shouldContain 나눠준_비율_할인_쿠폰
        }
    }
})
