package com.example.estdelivery.shop.controller

import com.example.estdelivery.shop.service.ShopOwnerService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ShopOwnerController(
    private val shopOwnerService: ShopOwnerService,
) {
    @GetMapping(
        value = ["/owners/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findShopOwner(@PathVariable id: Long) = shopOwnerService.findShopOwnerById(id)

    @GetMapping(
        value = ["/owners/shop/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findShopOwnerByShopId(@PathVariable id: Long) = shopOwnerService.findShopOwnerByShopId(id)

    @PostMapping(
        value = ["/owners/shop/{shopId}/royal-customers/{royalCustomerId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addRoyalCustomers(
        @PathVariable shopId: Long,
        @PathVariable royalCustomerId: Long
    ) = shopOwnerService.addRoyalCustomers(shopId, royalCustomerId)
}
