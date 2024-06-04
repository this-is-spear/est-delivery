package com.example.estdelivery.coupon.application.port.out.adapter.infra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = ["com.example.estdelivery.shop:shop:1.0-SNAPSHOT:8082"]
)
class ShopInfraAdapterTest(
    @Autowired
    private val shopInfraAdapter: ShopInfraAdapter,
) {

    @Test
    fun `가게 주인 식별자로 가게 주인을 찾는다`() {
        val shopOwner = shopInfraAdapter.findShopOwner(1L)
        assertThat(shopOwner.id).isEqualTo(1L)
    }

    @Test
    fun `가게 식별자로 가게 주인을 찾는다`() {
        val shopOwner = shopInfraAdapter.findShopOwnerByShopId(1L)
        assertThat(shopOwner.id).isEqualTo(1L)
    }

    @Test
    fun `가게 주인에게 단골 소님을 추가한다`() {
        shopInfraAdapter.addRoyalCustomers(3L, 7L)
    }

    @Test
    fun `1000번 식별자를 조회하면 400 에러가 발생한다`() {
        assertThrows<HttpClientErrorException.BadRequest> {
            shopInfraAdapter.findShopOwner(1000)
        }
    }

    @Test
    fun `1001번 식별자를 조회하면 429 에러가 발생한다`() {
        assertThrows<HttpClientErrorException.TooManyRequests> {
            shopInfraAdapter.findShopOwner(1001)
        }
    }

    @Test
    fun `1100번 식별자를 조회하면 500 에러가 발생한다`() {
        assertThrows<HttpServerErrorException.InternalServerError> {
            shopInfraAdapter.findShopOwner(1100)
        }
    }

    @Test
    fun `1101번 식별자를 조회하면 503 에러가 발생한다`() {
        assertThrows<HttpServerErrorException.ServiceUnavailable> {
            shopInfraAdapter.findShopOwner(1101)
        }
    }
}
