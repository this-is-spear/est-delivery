package com.example.estdelivery.coupon.application.port.out.adapter

import com.example.estdelivery.coupon.application.port.out.LoadShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateShopOwnerStatePort
import com.example.estdelivery.coupon.application.port.out.adapter.infra.ShopInfraAdapter
import com.example.estdelivery.coupon.application.port.out.adapter.infra.dto.ShopOwnerState
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.ShopOwnerPersistenceAdapter
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.member.Member
import com.example.estdelivery.coupon.domain.shop.HandOutCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedEventCouponBook
import com.example.estdelivery.coupon.domain.shop.RoyalCustomers
import com.example.estdelivery.coupon.domain.shop.Shop
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import com.example.estdelivery.coupon.domain.shop.UsedCouponBook
import org.springframework.stereotype.Component

@Component
class ShopOwnerAdapter(
    memberAdapter: MemberAdapter,
    shopInfraAdapter: ShopInfraAdapter,
    shopOwnerPersistenceAdapter: ShopOwnerPersistenceAdapter,
) : LoadShopOwnerStatePort, UpdateShopOwnerStatePort {

    override fun findById(shopOwnerId: Long): ShopOwner {
        return getShopOwner(shopOwnerId)
    }

    override fun findByShopId(shopId: Long): ShopOwner {
        return getShopOwnerByShopId(shopId)
    }

    override fun updateShopOwnersCoupons(shopOwner: ShopOwner) {
        updateShopOwnersCoupon(shopOwner)
    }

    override fun updateRoyalCustomers(shopOwner: ShopOwner, royalCustomer: Member) {
        updateRoyalCustomersInShopOwner(shopOwner, royalCustomer)
    }

    private val getShopOwner: (Long) -> ShopOwner = { shopOwnerId ->
        val shopOwnerState = shopInfraAdapter.findShopOwner(shopOwnerId)
        getShopOwner(shopOwnerPersistenceAdapter, shopOwnerState, memberAdapter)
    }
    private val getShopOwnerByShopId: (Long) -> ShopOwner = { shopId ->
        val shopOwnerState = shopInfraAdapter.findShopOwnerByShopId(shopId)
        getShopOwner(shopOwnerPersistenceAdapter, shopOwnerState, memberAdapter)
    }
    private val updateShopOwnersCoupon: (ShopOwner) -> Unit = { shopOwner ->
        shopOwnerPersistenceAdapter.update(shopOwner)
    }

    private val updateRoyalCustomersInShopOwner: (ShopOwner, Member) -> Unit = { shopOwner, royalCustomer ->
        shopInfraAdapter.addRoyalCustomers(shopOwner.showShop().id, royalCustomer.id)
    }

    private fun getShopOwner(
        shopOwnerPersistenceAdapter: ShopOwnerPersistenceAdapter,
        shopOwnerState: ShopOwnerState,
        memberAdapter: MemberAdapter
    ): ShopOwner {
        val shopOwnersCoupons = shopOwnerPersistenceAdapter.findByShopId(shopOwnerState.shop.id)
        val royalCustomers = shopOwnerState.shop.royalCustomers.map { memberId ->
            memberAdapter.findMember(memberId)
        }
        return ShopOwner(
            Shop(
                PublishedCouponBook(CouponBook(shopOwnersCoupons.showPublishedCouponsInShop())),
                PublishedEventCouponBook(CouponBook(shopOwnersCoupons.showEventCouponInShop())),
                HandOutCouponBook(CouponBook(shopOwnersCoupons.showHandOutCouponInShop())),
                UsedCouponBook(CouponBook(shopOwnersCoupons.showUsedCouponBook())),
                RoyalCustomers(royalCustomers),
                shopOwnerState.shop.id,
                shopOwnerState.shop.name,
            ),
            shopOwnerState.id,
        )
    }
}
