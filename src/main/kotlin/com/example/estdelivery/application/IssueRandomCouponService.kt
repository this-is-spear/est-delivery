package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.IssueEventCouponUseCase
import com.example.estdelivery.application.port.`in`.command.IssueEventCouponCommand
import com.example.estdelivery.application.port.out.*
import com.example.estdelivery.application.port.out.state.UpdateRandomCouponIssueEventState
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.event.RandomCouponIssueEvent
import com.example.estdelivery.domain.member.Member
import com.example.estdelivery.domain.shop.ShopOwner

class IssueRandomCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadRandomCouponIssueEventStatePort: LoadRandomCouponIssueEventStatePort,
    loadShopOwnerStatePort: LoadShopOwnerStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    updateRandomCouponIssueEventStatePort: UpdateRandomCouponIssueEventStatePort,
    createCouponStatePort: CreateCouponStatePort,
    private val transactionArea: TransactionArea,
    private val getMember: (IssueEventCouponCommand) -> Member = {
        loadMemberStatePort.findById(it.memberId)
    },
    private val getCouponIssueRandomCouponIssueEvent: (IssueEventCouponCommand) -> RandomCouponIssueEvent = {
        loadRandomCouponIssueEventStatePort.findById(it.eventId).toEvent()
    },
    private val getShopOwner: (IssueEventCouponCommand) -> ShopOwner = {
        loadShopOwnerStatePort.findByShopId(it.shopId)
    },
    private val createCoupon: (Coupon) -> Coupon = { createCouponStatePort.create(it) },
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.update(it) },
    private val updateShopOwner: (ShopOwner) -> Unit = { updateShopOwnerStatePort.update(it) },
    private val updateEvent: (RandomCouponIssueEvent) -> Unit = {
        updateRandomCouponIssueEventStatePort.update(UpdateRandomCouponIssueEventState.from(it))
    }
) : IssueEventCouponUseCase {
    /**
     * 1. 회원 정보를 조회한다.
     * 2. 가게 주인은 이벤트에 맞는 쿠폰을 발급한다.
     * 3. 회원에게 전달한다.
     */
    override fun issueEventCoupon(issueEventCouponCommand: IssueEventCouponCommand) {
        transactionArea.run {
            val member = getMember(issueEventCouponCommand)
            val event = getCouponIssueRandomCouponIssueEvent(issueEventCouponCommand)
            val createdCoupon = createCoupon(event.participateCreateCouponEvent(member))
            val shopOwner = getShopOwner(issueEventCouponCommand)
            val eventCoupon = shopOwner.issueEventCouponInShop(createdCoupon)
            member.receiveCoupon(eventCoupon)
            updateMember(member)
            updateShopOwner(shopOwner)
            updateEvent(event)
        }
    }
}
