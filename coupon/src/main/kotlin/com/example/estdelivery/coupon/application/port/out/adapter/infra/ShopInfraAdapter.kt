package com.example.estdelivery.coupon.application.port.out.adapter.infra

import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.ShopOwnerState
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class ShopInfraAdapter(
    private val shopClient: RestClient,
) {
    fun findShopOwner(shopOwnerId: Long): ShopOwnerState {
        return shopClient.get()
            .uri("/owners/{shopOwnerId}", shopOwnerId)
            .retrieve()
            .body(ShopOwnerState::class.java)
            ?: throw RuntimeException("ShopOwner not found")
    }

    fun findShopOwnerByShopId(shopId: Long): ShopOwnerState {
        return shopClient.get()
            .uri("/owners/shop/{shopId}", shopId)
            .retrieve()
            .body(ShopOwnerState::class.java)
            ?: throw RuntimeException("ShopOwner not found")
    }

    fun addRoyalCustomers(shopId: Long, royalCustomer: Long) {
        shopClient.post()
            .uri("/owners/shop/{shopId}/royal-customers/{royalCustomer}", shopId, royalCustomer)
            .retrieve()
    }
}
