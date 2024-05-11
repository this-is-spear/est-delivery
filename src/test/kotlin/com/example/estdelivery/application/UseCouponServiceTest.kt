package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.command.UseCouponCommand
import com.example.estdelivery.application.port.out.*
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.fixture.나눠준_비율_할인_쿠폰
import com.example.estdelivery.domain.fixture.나눠준_쿠폰을_가진_삼건창
import com.example.estdelivery.domain.fixture.나눠준_쿠폰이_있는_프리퍼
import com.example.estdelivery.domain.member.Member
import com.example.estdelivery.domain.shop.ShopOwner
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class UseCouponServiceTest : FreeSpec({

    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val loadShopOwnerStatePort = mockk<LoadShopOwnerStatePort>()
    val loadCouponStatePort = mockk<LoadCouponStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()
    val updateShopOwnerStatePort = mockk<UpdateShopOwnerStatePort>()
    val transactionArea = TransactionArea()

    lateinit var useCouponService: UseCouponService

    beforeTest {
        useCouponService = UseCouponService(
            loadMemberStatePort,
            loadCouponStatePort,
            loadShopOwnerStatePort,
            updateMemberStatePort,
            updateShopOwnerStatePort,
            transactionArea
        )
    }

    "회원은 가게에 쿠폰을 사용할 수 있다." {
        // given
        val 프리퍼_주인 = ShopOwner(나눠준_쿠폰이_있는_프리퍼(나눠준_비율_할인_쿠폰), 1L)
        val 회원 = 나눠준_쿠폰을_가진_삼건창()
        val memberId = 회원.id
        val shopId = 프리퍼_주인.showShop().id!!
        val couponId = 나눠준_비율_할인_쿠폰.id!!
        val useCouponCommand = UseCouponCommand(
            memberId,
            shopId,
            couponId
        )
        val 변경된_회원_상태 = slot<Member>()
        val 변경된_프리퍼_주인_상태 = slot<ShopOwner>()

        // when
        every { loadMemberStatePort.findById(memberId) } returns 회원
        every { loadCouponStatePort.findById(couponId) } returns 나눠준_비율_할인_쿠폰
        every { loadShopOwnerStatePort.findByShopId(shopId) } returns 프리퍼_주인
        every { updateMemberStatePort.update(capture(변경된_회원_상태)) } returns Unit
        every { updateShopOwnerStatePort.update(capture(변경된_프리퍼_주인_상태)) } returns Unit

        useCouponService.useCoupon(useCouponCommand)

        // then
        변경된_회원_상태.captured.showMyCouponBook() shouldNotContain 나눠준_비율_할인_쿠폰
        변경된_프리퍼_주인_상태.captured.showUsedCouponBook() shouldContain 나눠준_비율_할인_쿠폰
    }
})
