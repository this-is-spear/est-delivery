package com.example.estdelivery.shop

import com.example.estdelivery.shop.controller.ShopOwnerController
import com.example.estdelivery.shop.dto.ShopOwnerResponse
import com.example.estdelivery.shop.service.ShopOwnerService
import io.mockk.every
import io.mockk.mockk
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach

open class ShopBase {
    @BeforeEach
    fun setup() {
        val shopOwnerService = mockk<ShopOwnerService>()
        every { shopOwnerService.findShopOwnerById(1) } returns ShopOwnerResponse(1, 1, "첫 번째 가게")
        every { shopOwnerService.findShopOwnerById(2) } returns ShopOwnerResponse(2, 2, "두 번째 가게")
        every { shopOwnerService.findShopOwnerByShopId(1) } returns ShopOwnerResponse(1, 1, "첫 번째 가게")
        every { shopOwnerService.findShopOwnerByShopId(2) } returns ShopOwnerResponse(2, 2, "두 번째 가게")
        RestAssuredMockMvc.standaloneSetup(ShopOwnerController(shopOwnerService))
    }
}
