package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.shop.ShopOwner

interface LoadShopOwnerStatePort {
    fun findById(shopOwnerId: Long): ShopOwner

    fun findByShopId(shopId: Long): ShopOwner
}
