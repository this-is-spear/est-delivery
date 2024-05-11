package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.UseCouponUseCase
import com.example.estdelivery.application.port.`in`.command.UseCouponCommand
import com.example.estdelivery.application.port.out.*
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.member.Member
import com.example.estdelivery.domain.shop.ShopOwner

class UseCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    loadShopOwnerStatePort: LoadShopOwnerStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    private val transactionArea: TransactionArea,
    private val getMember: (UseCouponCommand) -> Member = { loadMemberStatePort.findById(it.memberId) },
    private val getCoupon: (UseCouponCommand) -> Coupon = { loadCouponStatePort.findById(it.couponId) },
    private val getShopOwner: (UseCouponCommand) -> ShopOwner = { loadShopOwnerStatePort.findByShopId(it.shopId) },
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.update(it) },
    private val updateShopOwner: (ShopOwner) -> Unit = { updateShopOwnerStatePort.update(it) },
) : UseCouponUseCase {
    /**
     * 1. 회원 정보를 조회한다.
     * 2. 쿠폰 정보를 조회한다.
     * 3. 회원이 가진 쿠폰북에서 쿠폰을 꺼낸다.
     * 4. 가게주인은 사용할 쿠폰을 받는다.
     *
     * @param useCouponCommand 사용할 쿠폰 정보와 회원 정보
     */
    override fun useCoupon(useCouponCommand: UseCouponCommand) {
        transactionArea.run {
            val member = getMember(useCouponCommand)
            val coupon = getCoupon(useCouponCommand)
            val shopOwner = getShopOwner(useCouponCommand)

            member.useCoupon(coupon)
            shopOwner.receiveCoupon(coupon)

            updateMember(member)
            updateShopOwner(shopOwner)
        }
    }
}
