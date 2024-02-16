package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.PublishCouponUseCase
import com.example.estdelivery.application.port.`in`.command.PublishCouponCommand
import com.example.estdelivery.application.port.out.CreateCouponStatePort
import com.example.estdelivery.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.application.port.out.UpdateShopOwnerStatePort

class PublishCouponService(
    private val loadShopOwnerPort: LoadShopOwnerStatePort,
    private val createCouponStatePort: CreateCouponStatePort,
    private val updateShopOwnerStatePort: UpdateShopOwnerStatePort,
) : PublishCouponUseCase {
    /**
     * 1. 가게 주인 정보를 조회한다.
     * 2. 가게 정보를 조회한다.
     * 3. 쿠폰을 생성한다.
     * 4. 가게에 게시된 쿠폰북에 게시한다.
     *
     * @param publishCouponCommand 게시할 쿠폰 정보와 가게 주인 정보
     */
    override fun publishCoupon(publishCouponCommand: PublishCouponCommand) {
        val shopOwner = loadShopOwnerPort.findById(publishCouponCommand.shopOwnerId)
        val publishedCoupon = createCouponStatePort.create(publishCouponCommand.coupon)
        shopOwner.publishCouponInShop(publishedCoupon)
        updateShopOwnerStatePort.update(shopOwner)
    }
}
