package com.example.estdelivery.shop

import com.example.estdelivery.shop.controller.BadRequestException
import com.example.estdelivery.shop.controller.InternalServerErrorException
import com.example.estdelivery.shop.controller.ServiceUnavailableException
import com.example.estdelivery.shop.controller.TooManyRequestsException
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
            val id = firstArg<Long>()
            handleDefaultException(id)

            ShopOwnerResponse(
                id,
                ShopResponse(listOf(1, 2, 3), "가게", id)
            )
        }
        every { shopOwnerService.findShopOwnerByShopId(any()) } answers {
            val id = firstArg<Long>()
            handleDefaultException(id)

            ShopOwnerResponse(
                id,
                ShopResponse(listOf(1, 2, 3), "가게", id)
            )
        }
        every { shopOwnerService.addRoyalCustomers(any(), any()) } answers {
            val id = secondArg<Long>()
            handleDefaultException(id)
        }
        RestAssuredMockMvc.webAppContextSetup(this.context)
    }

    private fun handleDefaultException(id: Long) {
        if (id == 1000L) {
            throw BadRequestException()
        }

        if (id == 1001L) {
            throw TooManyRequestsException()
        }

        if (id == 1100L) {
            throw InternalServerErrorException()
        }

        if (id == 1101L) {
            throw ServiceUnavailableException()
        }
    }
}
