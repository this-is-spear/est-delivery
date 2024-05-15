package com.example.estdelivery.coupon.application.port.out

import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.shop.ShopOwner

interface UpdateShopOwnerStatePort {
    fun updateShopOwnersCoupons(shopOwner: ShopOwner)
    fun updateRoyalCustomers(shopOwner: ShopOwner, royalCustomer: Member)
}
