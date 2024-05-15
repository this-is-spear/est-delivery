package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.UseCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.command.UseCouponCommand
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
class UseCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    loadShopOwnerStatePort: LoadShopOwnerStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    private val transactionArea: TransactionArea,
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

    private val getMember: (UseCouponCommand) -> Member = {
        loadMemberStatePort.findMember(
            it.memberId
        )
    }
    private val getCoupon: (UseCouponCommand) -> Coupon = {
        loadCouponStatePort.findById(
            it.couponId
        )
    }
    private val getShopOwner: (UseCouponCommand) -> ShopOwner = {
        loadShopOwnerStatePort.findByShopId(
            it.shopId
        )
    }
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) }
    private val updateShopOwner: (ShopOwner) -> Unit = { updateShopOwnerStatePort.updateShopOwnersCoupons(it) }
}
