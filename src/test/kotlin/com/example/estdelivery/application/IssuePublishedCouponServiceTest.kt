package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.command.IssuePublishedCouponCommand
import com.example.estdelivery.application.port.out.*
import com.example.estdelivery.application.port.out.state.CouponState
import com.example.estdelivery.application.port.out.state.MemberState
import com.example.estdelivery.application.port.out.state.PublishedCouponBookState
import com.example.estdelivery.application.port.out.state.ShopState
import io.kotest.core.spec.style.FreeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

/**
 * - 회원은 가게에 게시된 쿠폰북에서 쿠폰을 꺼내 자신의 쿠폰 북에 담는다.
 * - 이미 가진 쿠폰이라면 발행 할 수 없다.
 * - 게시되지 않은 쿠폰은 발행 될 수 없다.
 */
class IssuePublishedCouponServiceTest : FreeSpec({

    val loadCouponStatePort = mockk<LoadCouponStatePort>()
    val loadShopStatePort = mockk<LoadShopStatePort>()
    val loadPublishedCouponBookStatePort = mockk<LoadPublishedCouponBookStatePort>()
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val updateShopStatePort = mockk<UpdateShopStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()

    lateinit var issuePublishedCouponUseCase: IssuePublishedCouponService

    beforeTest {
        issuePublishedCouponUseCase = IssuePublishedCouponService(
            loadMemberStatePort,
            loadPublishedCouponBookStatePort,
            loadCouponStatePort,
            loadShopStatePort,
            updateMemberStatePort,
            updateShopStatePort
        )
    }

    /**
     * state 클래스를 비교해 검증합니다.
     * state는 id를 가지고 비교하게 된다면 상태 변경을 포착하기 어렵습니다.
     * 따라서 state 상태 전부를 비교하여 검증합니다.
     */
    "회원은 가게에 게시된 쿠폰북에서 쿠폰을 꺼내 자신의 쿠폰 북에 담는다." {
        // given
        val memberId = 1L
        val shopId = 1L
        val couponId = 1L
        val issuePublishedCouponCommand = IssuePublishedCouponCommand(couponId, memberId, shopId)
        val 할인쿠폰_10퍼센트_상태 = CouponState("10% 할인 쿠폰", "게시된 10% 할인 쿠폰입니다.", "RATE", "PUBLISHED", 10, couponId)
        val 할인쿠폰_10퍼센트 = 할인쿠폰_10퍼센트_상태.toCoupon()

        val 회원_상태 = MemberState("name", listOf(), memberId)

        every { loadMemberStatePort.findById(memberId) } returns 회원_상태
        every { loadPublishedCouponBookStatePort.findById(shopId) } returns PublishedCouponBookState(listOf(할인쿠폰_10퍼센트_상태))
        every { loadCouponStatePort.findByCouponId(couponId) } returns 할인쿠폰_10퍼센트_상태
        every { updateMemberStatePort.updateMember(any()) } returns Unit
        every { loadShopStatePort.findById(shopId) } returns ShopState("프리퍼", listOf(), listOf(), listOf(), listOf())
        every { updateShopStatePort.update(any()) } returns Unit

        // when
        issuePublishedCouponUseCase.issuePublishedCoupon(issuePublishedCouponCommand)

        // then
        val 변경된_회원상태 = MemberState("name", listOf(할인쿠폰_10퍼센트), memberId)

        verify { updateMemberStatePort.updateMember(변경된_회원상태) }
        verify { updateShopStatePort.update(ShopState("프리퍼", listOf(), listOf(), listOf(), listOf(변경된_회원상태.toMember()))) }
    }
})