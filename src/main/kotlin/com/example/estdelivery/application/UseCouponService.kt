package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.UseCouponUseCase
import com.example.estdelivery.application.port.`in`.command.UseCouponCommand
import com.example.estdelivery.application.port.out.*
import com.example.estdelivery.application.port.out.state.MemberState
import com.example.estdelivery.application.port.out.state.ShopOwnerState

class UseCouponService(
    private val loadMemberStatePort: LoadMemberStatePort,
    private val loadCouponStatePort: LoadCouponStatePort,
    private val loadShopOwnerStatePort: LoadShopOwnerStatePort,
    private val updateMemberStatePort: UpdateMemberStatePort,
    private val updateShopOwnerStatePort: UpdateShopOwnerStatePort,
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
        val member = loadMemberStatePort.findById(useCouponCommand.memberId).toMember()
        val coupon = loadCouponStatePort.findById(useCouponCommand.couponId).toCoupon()
        val shopOwner = loadShopOwnerStatePort.findByShopId(useCouponCommand.shopId).toShopOwner()

        member.useCoupon(coupon)
        shopOwner.receiveCoupon(coupon)

        updateMemberStatePort.update(MemberState.from(member))
        updateShopOwnerStatePort.update(ShopOwnerState.from(shopOwner))
    }
}