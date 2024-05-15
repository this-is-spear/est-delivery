package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopOwnerEntity
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface ShopOwnerRepository :
    JpaRepository<ShopOwnerEntity, Long> {
    fun findByShopEntity(shopEntity: ShopEntity): Optional<ShopOwnerEntity>
}
