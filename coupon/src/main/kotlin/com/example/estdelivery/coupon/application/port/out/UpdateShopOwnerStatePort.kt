package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.shop.ShopOwner

interface UpdateShopOwnerStatePort {
    fun update(shopOwner: ShopOwner)
}
