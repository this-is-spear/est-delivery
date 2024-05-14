package com.example.estdelivery.shop.repository

import com.example.estdelivery.shop.entity.ShopOwner
import org.springframework.data.jpa.repository.JpaRepository

interface ShopOwnerRepository: JpaRepository<ShopOwner, Long> {
    fun findByShopId(shopId: Long): ShopOwner?
}
