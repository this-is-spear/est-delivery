package com.example.estdelivery.coupon.application.port.out.infra

import com.example.estdelivery.coupon.application.port.out.infra.dto.ShopOwnerState
import com.example.estdelivery.coupon.domain.shop.HandOutCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedEventCouponBook
import com.example.estdelivery.coupon.domain.shop.RoyalCustomers
import com.example.estdelivery.coupon.domain.shop.Shop
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import com.example.estdelivery.coupon.domain.shop.UsedCouponBook
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ShopInfraAdapter(
    private val shopClient: RestClient,
) {
    fun findShopOwner(shopOwnerId: Long): ShopOwner {
        return shopClient.get()
            .uri("/owners/{shopOwnerId}", shopOwnerId)
            .retrieve()
            .body(ShopOwnerState::class.java)?.let {
                ShopOwner(
                    id = it.id,
                    shop = it.shop.let { shopState ->
                        Shop(
                            PublishedCouponBook(),
                            PublishedEventCouponBook(),
                            HandOutCouponBook(),
                            UsedCouponBook(),
                            RoyalCustomers(),
                            shopState.name,
                            shopState.id,
                        )
                    },
                )
            } ?: throw RuntimeException("ShopOwner not found")
    }

    fun findShopOwnerByShopId(shopId: Long): ShopOwner {
        return shopClient.get()
            .uri("/owners/shop/{shopId}", shopId)
            .retrieve()
            .body(ShopOwnerState::class.java)?.let {
                ShopOwner(
                    id = it.id,
                    shop = it.shop.let { shopState ->
                        Shop(
                            PublishedCouponBook(),
                            PublishedEventCouponBook(),
                            HandOutCouponBook(),
                            UsedCouponBook(),
                            RoyalCustomers(),
                            shopState.name,
                            shopState.id,
                        )
                    },
                )
            } ?: throw RuntimeException("ShopOwner not found")
    }
}
