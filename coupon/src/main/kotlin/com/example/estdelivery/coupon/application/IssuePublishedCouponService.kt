package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.IssuePublishedCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.command.IssuePublishedCouponCommand
import com.example.estdelivery.coupon.application.port.out.LoadCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import org.springframework.stereotype.Service

@Service
class IssuePublishedCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    loadShopOwnerStatePort: LoadShopOwnerStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    private val transactionArea: TransactionArea,
) : IssuePublishedCouponUseCase {
    /**
     * 1. 회원 정보를 조회한다.
     * 2. 쿠폰정보를 조회한다.
     * 3. 가게가 가진 게시된 쿠폰 북에서 쿠폰을 발행한다.
     * 4. 발급된 쿠폰을 사용자의 쿠폰북에 추가한다.
     * 5. 가게 단골 손님으로 등록한다.
     *
     * @param issuePublishedCouponCommand 발행된 쿠폰을 사용자에게 발급하는 명령
     */
    override fun issuePublishedCoupon(issuePublishedCouponCommand: IssuePublishedCouponCommand) {
        transactionArea.run {
            val member = getMember(issuePublishedCouponCommand)
            val shopOwner = getShopOwner(issuePublishedCouponCommand)
            val coupon = getCoupon(issuePublishedCouponCommand)
            member.receiveCoupon(shopOwner.issuePublishedCouponInShop(coupon))
            updateMember(member)
            addRoyalCustomers(shopOwner, member)
        }
    }

    private fun addRoyalCustomers(
        shopOwner: ShopOwner,
        member: Member,
    ) {
        if (!shopOwner.showRoyalCustomersInShop().contains(member)) {
            shopOwner.addRoyalCustomersInShop(member)
            updateRoyalCustomers(shopOwner, member)
        }
    }

    private val getMember: (IssuePublishedCouponCommand) -> Member = {
        loadMemberStatePort.findMember(it.memberId)
    }
    private val getCoupon: (IssuePublishedCouponCommand) -> Coupon = {
        loadCouponStatePort.findById(
            it.couponId
        )
    }
    private val getShopOwner: (IssuePublishedCouponCommand) -> ShopOwner = {
        loadShopOwnerStatePort.findByShopId(
            it.shopId
        )
    }
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) }
    private val updateRoyalCustomers: (ShopOwner, Member) -> Unit = { shopOwner, royalCustomer ->
        updateShopOwnerStatePort.updateRoyalCustomers(shopOwner, royalCustomer)
    }
}
