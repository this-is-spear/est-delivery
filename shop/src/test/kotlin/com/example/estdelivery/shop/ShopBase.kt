package com.example.estdelivery.shop

import com.example.estdelivery.shop.dto.ShopOwnerResponse
import com.example.estdelivery.shop.dto.ShopResponse
import com.example.estdelivery.shop.service.ShopOwnerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
open class ShopBase {
    @Autowired
    lateinit var context: WebApplicationContext

    @MockkBean
    lateinit var shopOwnerService: ShopOwnerService

    @BeforeEach
    fun setup() {
        every { shopOwnerService.findShopOwnerById(any()) } answers {
            val capturedId = firstArg<Long>()

            if (capturedId % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }

            ShopOwnerResponse(
                capturedId,
                ShopResponse(listOf(1, 3, 5), "가게", capturedId)
            )
        }
        every { shopOwnerService.findShopOwnerByShopId(any()) } answers {
            val capturedId = firstArg<Long>()
            if (capturedId % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }

            ShopOwnerResponse(
                capturedId,
                ShopResponse(listOf(1, 3, 5), "가게", capturedId)
            )
        }
        every { shopOwnerService.addRoyalCustomers(any(), any()) } answers {
            if (secondArg<Long>() % 2 == 0L) {
                throw IllegalArgumentException("Invalid id")
            }
        }
        RestAssuredMockMvc.webAppContextSetup(this.context)
    }
}
