package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.command.IssueEventCouponCommand
import com.example.estdelivery.coupon.application.port.out.CreateCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.LoadRandomCouponIssueEventStatePort
import com.example.estdelivery.coupon.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateRandomCouponIssueEventStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.state.LoadRandomCouponIssueEventState
import com.example.estdelivery.coupon.application.port.out.state.UpdateRandomCouponIssueEventState
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.event.DiscountAmountProbability
import com.example.estdelivery.coupon.domain.event.DiscountRange
import com.example.estdelivery.coupon.domain.event.EventDiscountType
import com.example.estdelivery.coupon.domain.event.ProbabilityRange
import com.example.estdelivery.coupon.domain.fixture.새로_창업해서_아무것도_없는_프리퍼
import com.example.estdelivery.coupon.domain.fixture.이건창
import com.example.estdelivery.coupon.domain.fixture.이벤트_쿠폰
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class IssueRandomCouponServiceTest : FreeSpec({
    val loadShopOwnerStatePort = mockk<LoadShopOwnerStatePort>()
    val loadMemberStatePort = mockk<LoadMemberStatePort>()
    val updateMemberStatePort = mockk<UpdateMemberStatePort>()
    val updateShopOwnerStatePort = mockk<UpdateShopOwnerStatePort>()
    val createCouponStatePort = mockk<CreateCouponStatePort>()
    val transactionArea = TransactionArea()
    val loadRandomCouponIssueEventStatePort = mockk<LoadRandomCouponIssueEventStatePort>()
    val updateRandomCouponIssueEventStatePort = mockk<UpdateRandomCouponIssueEventStatePort>()

    lateinit var issueRandomCouponService: IssueRandomCouponService

    beforeTest {
        issueRandomCouponService =
            IssueRandomCouponService(
                loadMemberStatePort,
                loadRandomCouponIssueEventStatePort,
                loadShopOwnerStatePort,
                updateMemberStatePort,
                updateShopOwnerStatePort,
                updateRandomCouponIssueEventStatePort,
                createCouponStatePort,
                transactionArea,
            )
    }

    val 이벤트_쿠폰_발급 =
        IssueEventCouponCommand(
            memberId = 1L,
            shopId = 1L,
            eventId = 1L,
        )
    val 가게주인 = ShopOwner(새로_창업해서_아무것도_없는_프리퍼(), 2L)
    val 랜덤_쿠폰_뽑기_이벤트_상태 =
        LoadRandomCouponIssueEventState(
            id = 1L,
            description = "고정 할인 쿠폰 제공 이벤트",
            isProgress = true,
            disCountType = EventDiscountType.FIXED,
            probabilityRanges =
            DiscountAmountProbability(
                listOf(
                    ProbabilityRange(1000, 1500, 0.5),
                    ProbabilityRange(1200, 2000, 0.3),
                    ProbabilityRange(2100, 3000, 0.2),
                ),
                DiscountRange(1000, 3000),
            ),
            participatedMembers = emptyList(),
        )
    val 이벤트_쿠폰 = 이벤트_쿠폰

    "이벤트로 고정 할인 쿠폰을 발급한다." {
        // given
        val 상태가_변경된_이벤트 = slot<UpdateRandomCouponIssueEventState>()
        val 상태가_변경된_가게주인 = slot<ShopOwner>()
        val 상태가_변경된_회원 = slot<Member>()
        val 이건창 = 이건창()

        // when
        every { loadMemberStatePort.findById(1L) } returns 이건창
        every { loadRandomCouponIssueEventStatePort.findById(1L) } returns 랜덤_쿠폰_뽑기_이벤트_상태
        every { loadShopOwnerStatePort.findByShopId(1L) } returns 가게주인
        every { updateMemberStatePort.update(capture(상태가_변경된_회원)) } returns Unit
        every { updateShopOwnerStatePort.update(capture(상태가_변경된_가게주인)) } returns Unit
        every { updateRandomCouponIssueEventStatePort.update(capture(상태가_변경된_이벤트)) } returns Unit
        every { createCouponStatePort.create(any()) } returns 이벤트_쿠폰

        issueRandomCouponService.issueEventCoupon(이벤트_쿠폰_발급)

        // then
        상태가_변경된_이벤트.captured.participatedMembers.size shouldBe 1
        상태가_변경된_가게주인.captured.showEventCouponInShop().size shouldBe 1
        상태가_변경된_회원.captured.showMyCouponBook().size shouldBe 1
    }
})
