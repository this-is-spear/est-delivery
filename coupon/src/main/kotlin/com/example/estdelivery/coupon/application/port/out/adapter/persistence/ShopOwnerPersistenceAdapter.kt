package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromShopOwner
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toShopOwner
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.ShopOwnerRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.ShopRepository
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ShopOwnerPersistenceAdapter(
    private val shopOwnerRepository: ShopOwnerRepository,
    private val shopRepository: ShopRepository,
) {
    @Transactional(readOnly = true)
    fun findById(shopOwnerId: Long): ShopOwner {
        val shopOwnerEntity = shopOwnerRepository.findById(shopOwnerId).orElseThrow()
        return toShopOwner(shopOwnerEntity)
    }

    @Transactional(readOnly = true)
    fun findByShopId(shopId: Long): ShopOwner {
        val shopEntity = shopRepository.findById(shopId).orElseThrow()
        val shopOwnerEntity = shopOwnerRepository.findByShopEntity(shopEntity).orElseThrow()
        return toShopOwner(shopOwnerEntity)
    }

    @Transactional
    fun update(shopOwner: ShopOwner) {
        shopOwnerRepository.save(fromShopOwner(shopOwner))
    }
}
