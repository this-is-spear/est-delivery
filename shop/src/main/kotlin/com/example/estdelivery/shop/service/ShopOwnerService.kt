package com.example.estdelivery.shop.service

import com.example.estdelivery.shop.dto.ShopOwnerResponse
import com.example.estdelivery.shop.repository.ShopOwnerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ShopOwnerService(
    private val shopOwnerRepository: ShopOwnerRepository,
) {
    fun findShopOwnerById(id: Long): ShopOwnerResponse {
        val shopOwner = shopOwnerRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Shop owner not found")

        check(shopOwner.shop.id == id) { "Shop owner with id $id is not a shop owner" }
        check(shopOwner.shop.id != null) { "Shop owner with id $id has no shop id" }

        return ShopOwnerResponse(
            shopOwnerId = id,
            shopId = shopOwner.shop.id!!,
            shopName = shopOwner.shop.name,
        )
    }

    fun findShopOwnerByShopId(shopId: Long): ShopOwnerResponse {
        val shopOwner = shopOwnerRepository.findByShopId(shopId)
            ?: throw IllegalArgumentException("Shop owner not found")

        check(shopOwner.shop.id == shopId) { "Shop owner with shop id $shopId is not a shop owner" }
        check(shopOwner.shop.id != null) { "Shop owner with shop id $shopId has no shop id" }
        check(shopOwner.id != null) { "Shop owner with shop id $shopId has no shop owner id" }

        return ShopOwnerResponse(
            shopOwnerId = shopOwner.id!!,
            shopId = shopOwner.shop.id!!,
            shopName = shopOwner.shop.name,
        )
    }
}
