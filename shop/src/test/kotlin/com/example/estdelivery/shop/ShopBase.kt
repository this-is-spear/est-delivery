package com.example.estdelivery.shop

import com.example.estdelivery.shop.controller.ShopOwnerController
import com.example.estdelivery.shop.dto.ShopOwnerResponse
import com.example.estdelivery.shop.dto.ShopResponse
import com.example.estdelivery.shop.service.ShopOwnerService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach

open class ShopBase {
    @BeforeEach
    fun setup() {
        val shopOwnerService = mockk<ShopOwnerService>()
        val id = slot<Long>()
        every { shopOwnerService.findShopOwnerById(capture(id)) } answers {
            ShopOwnerResponse(
                id.captured,
                ShopResponse(listOf(1, 2, 3), "가게", id.captured)
            )
        }
        every { shopOwnerService.findShopOwnerByShopId(capture(id)) } answers {
            ShopOwnerResponse(
                id.captured,
                ShopResponse(listOf(1, 2, 3), "가게", id.captured)
            )
        }
        every { shopOwnerService.addRoyalCustomers(any(), any()) } returns Unit
        RestAssuredMockMvc.standaloneSetup(ShopOwnerController(shopOwnerService))
    }
}
