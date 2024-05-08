package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.HandoutCouponUseCase
import com.example.estdelivery.application.port.`in`.command.HandoutCouponCommand
import com.example.estdelivery.application.port.out.CreateCouponStatePort
import com.example.estdelivery.application.port.out.LoadCouponStatePort
import com.example.estdelivery.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.application.port.out.state.CouponState
import com.example.estdelivery.application.port.out.state.ShopOwnerState
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.shop.ShopOwner

class HandoutCouponService(
    loadShopOwnerStatePort: LoadShopOwnerStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    createCouponStatePort: CreateCouponStatePort,
    private val getShopOwner: (HandoutCouponCommand) -> ShopOwner = { loadShopOwnerStatePort.findById(it.shopOwnerId).toShopOwner() },
    private val notExistCoupon: (Long) -> Boolean = { loadCouponStatePort.exists(it).not() },
    private val getCoupon: (Long) -> Coupon = { loadCouponStatePort.findById(it).toCoupon() },
    private val updateShopOwner: (ShopOwner) -> Unit = { updateShopOwnerStatePort.update(ShopOwnerState.from(it)) },
    private val createCoupon: (Coupon) -> Coupon = { createCouponStatePort.create(CouponState.from(it)).toCoupon() },
) : HandoutCouponUseCase {
    /**
     * 1. 가게 주인 정보를 조회한다.
     * 2. 가게 정보를 조회한다.
     * 3. 단골들 중 동일한 쿠폰을 아직 받지 않은 사용자에게 쿠폰을 뿌린다.
     *
     * @param handoutCouponCommand 나눠줄 쿠폰 정보와 가게 주인 정보
     */
    override fun handoutCoupon(handoutCouponCommand: HandoutCouponCommand) {
        val shopOwner = getShopOwner(handoutCouponCommand)
        val handoutCoupon: Coupon = getHandoutCoupon(handoutCouponCommand)
        shopOwner.handOutCouponToRoyalCustomersInShop(handoutCoupon)
        updateShopOwner(shopOwner)
    }

    private fun getHandoutCoupon(handoutCouponCommand: HandoutCouponCommand): Coupon {
        val id = handoutCouponCommand.coupon.id
        if (id == null || notExistCoupon(id)) {
            return createCoupon(handoutCouponCommand.coupon)
        }

        return getCoupon(id)
    }
}
