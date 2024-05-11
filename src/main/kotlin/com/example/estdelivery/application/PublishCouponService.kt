package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.PublishCouponUseCase
import com.example.estdelivery.application.port.`in`.command.PublishCouponCommand
import com.example.estdelivery.application.port.out.CreateCouponStatePort
import com.example.estdelivery.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.shop.ShopOwner

class PublishCouponService(
    loadShopOwnerPort: LoadShopOwnerStatePort,
    createCouponStatePort: CreateCouponStatePort,
    updateShopOwnerStatePort: UpdateShopOwnerStatePort,
    private val transactionArea: TransactionArea,
    private val findShopOwner: (PublishCouponCommand) -> ShopOwner = { loadShopOwnerPort.findById(it.shopOwnerId) },
    private val createCoupon: (PublishCouponCommand) -> Coupon = { createCouponStatePort.create(it.coupon) },
    private val updateShopOwner: (ShopOwner) -> Unit = { updateShopOwnerStatePort.update(it) },
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
        transactionArea.run {
            val shopOwner = findShopOwner(publishCouponCommand)
            val publishedCoupon = createCoupon(publishCouponCommand)
            shopOwner.publishCouponInShop(publishedCoupon)
            updateShopOwner(shopOwner)
        }
    }
}
