package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopOwnerEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.CouponRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.MemberRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.ShopOwnerRepository
import com.example.estdelivery.coupon.domain.fixture.게시할_쿠폰
import com.example.estdelivery.coupon.domain.fixture.나눠줄_쿠폰
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties

@SpringBootTest
@AutoConfigureStubRunner(
    stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = [
        "com.example.estdelivery.member:member:1.0-SNAPSHOT:8081",
        "com.example.estdelivery.shop:shop:1.0-SNAPSHOT:8082",
    ]
)
class ShopOwnerAdapterTest(
    @Autowired
    private val couponRepository: CouponRepository,
    @Autowired
    private val memberAdapter: MemberAdapter,
    @Autowired
    private val memberRepository: MemberRepository,
    @Autowired
    private val shopOwnerRepository: ShopOwnerRepository,
    @Autowired
    private val shopOwnerAdapter: ShopOwnerAdapter,
) {

    @BeforeEach
    fun setUp() {
        memberRepository.deleteAll()
        shopOwnerRepository.deleteAll()
        couponRepository.deleteAll()
    }

    @Test
    fun `가게 주인 식별자로 가게 주인을 찾는다`() {
        val shopEntity = ShopEntity(listOf(), listOf(), listOf(), listOf())
        val shopOwnerEntity = ShopOwnerEntity(shopEntity)

        val ownerEntity = shopOwnerRepository.save(shopOwnerEntity)

        // when
        val owner = shopOwnerAdapter.findById(ownerEntity.id!!)

        // then
        owner.showShop().id shouldBe ownerEntity.shopEntity.id
    }

    @Test
    fun `가게 식별자로 가게 주인을 찾는다`() {
        val shopEntity = ShopEntity(listOf(), listOf(), listOf(), listOf())
        val shopOwnerEntity = ShopOwnerEntity(shopEntity)
        val ownerEntity = shopOwnerRepository.save(shopOwnerEntity)

        val owner = shopOwnerAdapter.findByShopId(ownerEntity.shopEntity.id!!)

        // then
        owner.showShop().id shouldBe ownerEntity.shopEntity.id
    }

    @Test
    fun `수정된 쿠폰 목록을 저장한다`() {
        val shopEntity = ShopEntity(listOf(), listOf(), listOf(), listOf())
        val shopOwnerEntity = ShopOwnerEntity(shopEntity)
        val ownerEntity = shopOwnerRepository.save(shopOwnerEntity)

        val owner = shopOwnerAdapter.findById(ownerEntity.id!!)
        val 게시할_쿠폰 = toCoupon(couponRepository.save(fromCoupon(게시할_쿠폰)))
        val 나눠줄_쿠폰 = toCoupon(couponRepository.save(fromCoupon(나눠줄_쿠폰)))

        owner.publishCouponInShop(게시할_쿠폰)
        owner.handOutCouponToRoyalCustomersInShop(나눠줄_쿠폰)

        shopOwnerAdapter.updateShopOwnersCoupons(owner)

        val updatedOwner = shopOwnerAdapter.findById(owner.id)
        updatedOwner.showPublishedCouponsInShop() shouldBe listOf(게시할_쿠폰)
        updatedOwner.showHandOutCouponInShop() shouldBe listOf(나눠줄_쿠폰)
    }

    @Test
    fun `단골 고객을 추가한다`() {
        val shopEntity = ShopEntity(listOf(), listOf(), listOf(), listOf())
        val shopOwnerEntity = ShopOwnerEntity(shopEntity)
        val ownerEntity = shopOwnerRepository.save(shopOwnerEntity)
        val member = memberAdapter.findMember(10L)
        val owner = shopOwnerAdapter.findById(ownerEntity.id!!)

        owner.addRoyalCustomersInShop(member)

        shopOwnerAdapter.updateRoyalCustomers(owner, member)
    }
}
