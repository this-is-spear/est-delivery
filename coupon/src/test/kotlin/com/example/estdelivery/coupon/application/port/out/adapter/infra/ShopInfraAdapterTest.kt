package com.example.estdelivery.coupon.application.port.out.adapter.infra

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

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
        val shopOwner = shopInfraAdapter.findShopOwnerByShopId(2L)
        assertThat(shopOwner.id).isEqualTo(2L)
    }

    @Test
    fun `가게 주인에게 단골 소님을 추가한다`() {
        shopInfraAdapter.addRoyalCustomers(3L, 4L)
    }
}
