package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.command.PublishCouponCommand
import com.example.estdelivery.coupon.application.port.out.CreateCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.fixture.게시된_고정_할인_쿠폰
import com.example.estdelivery.coupon.domain.fixture.게시할_쿠폰
import com.example.estdelivery.coupon.domain.fixture.새로_창업해서_아무것도_없는_프리퍼
import com.example.estdelivery.coupon.domain.shop.HandOutCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedEventCouponBook
import com.example.estdelivery.coupon.domain.shop.RoyalCustomers
import com.example.estdelivery.coupon.domain.shop.Shop
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import com.example.estdelivery.coupon.domain.shop.UsedCouponBook
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class PublishCouponServiceTest : FreeSpec({
    val loadShopOwnerPort = mockk<LoadShopOwnerStatePort>()
    val createCouponStatePort = mockk<CreateCouponStatePort>()
    val updateShopOwnerStatePort = mockk<UpdateShopOwnerStatePort>()
    val transactionArea = TransactionArea()

    lateinit var publishCouponService: PublishCouponService

    beforeTest {
        publishCouponService =
            PublishCouponService(
                loadShopOwnerPort,
                createCouponStatePort,
                updateShopOwnerStatePort,
                transactionArea,
            )
    }

    "가게 주인은 쿠폰을 게시한다." {
        // given
        val shopOwnerId = 1L
        val 가게 = 새로_창업해서_아무것도_없는_프리퍼()
        val shopId = 가게.id
        val publishCouponCommand =
            PublishCouponCommand(
                shopOwnerId,
                shopId,
                게시할_쿠폰,
            )
        val 프리퍼_주인 = ShopOwner(가게, shopOwnerId)
        val 변경된_프리퍼_주인 = slot<ShopOwner>()

        // when
        every { loadShopOwnerPort.findById(shopOwnerId) } returns 프리퍼_주인
        every { createCouponStatePort.create(게시할_쿠폰) } returns 게시된_고정_할인_쿠폰
        every { updateShopOwnerStatePort.updateShopOwnersCoupons(capture(변경된_프리퍼_주인)) } returns Unit

        publishCouponService.publishCoupon(publishCouponCommand)

        // then
        변경된_프리퍼_주인.captured.showPublishedCouponsInShop() shouldContain 게시된_고정_할인_쿠폰
    }

    "게시된 쿠폰북에 동일한 쿠폰이 있을 수 없다." {
        // given
        val shopOwnerId = 1L
        val shopId = 새로_창업해서_아무것도_없는_프리퍼().id
        val publishCouponCommand =
            PublishCouponCommand(
                shopOwnerId,
                shopId,
                게시할_쿠폰,
            )
        val 이미_쿠폰을_게시한_프리퍼 =
            Shop(
                PublishedCouponBook(CouponBook(listOf(게시된_고정_할인_쿠폰))),
                PublishedEventCouponBook(),
                HandOutCouponBook(CouponBook(listOf())),
                UsedCouponBook(CouponBook(listOf())),
                RoyalCustomers(listOf()),
                shopId,
                "프리퍼",
            )
        val 프리퍼_주인_상태 = ShopOwner(이미_쿠폰을_게시한_프리퍼, shopOwnerId)

        // when

        every { loadShopOwnerPort.findById(shopOwnerId) } returns 프리퍼_주인_상태
        every { createCouponStatePort.create(게시할_쿠폰) } returns 게시된_고정_할인_쿠폰

        // then
        shouldThrow<IllegalArgumentException> {
            publishCouponService.publishCoupon(publishCouponCommand)
        }.message shouldBe "이미 게시한 쿠폰입니다."
    }
})
