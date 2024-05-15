package com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.MemberEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.ShopOwnerEntity
import com.example.estdelivery.coupon.domain.coupon.CouponBook
import com.example.estdelivery.coupon.domain.shop.HandOutCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedCouponBook
import com.example.estdelivery.coupon.domain.shop.PublishedEventCouponBook
import com.example.estdelivery.coupon.domain.shop.RoyalCustomers
import com.example.estdelivery.coupon.domain.shop.Shop
import com.example.estdelivery.coupon.domain.shop.ShopOwner
import com.example.estdelivery.coupon.domain.shop.UsedCouponBook

internal fun toShopOwner(entity: ShopOwnerEntity): ShopOwner {
    return ShopOwner(toShop(entity.shopEntity), entity.id!!)
}

internal fun fromShopOwner(shopOwner: ShopOwner): ShopOwnerEntity {
    return ShopOwnerEntity(
        fromShop(shopOwner.showShop()),
        shopOwner.id,
    )
}

internal fun toShop(entity: ShopEntity): Shop {
    return Shop(
        PublishedCouponBook(CouponBook(entity.publishedCouponBook.map {
            toCoupon(
                it
            )
        })),
        PublishedEventCouponBook(CouponBook(entity.publishedEventCouponBook.map {
            toCoupon(
                it
            )
        })),
        HandOutCouponBook(CouponBook(entity.handOutCouponBook.map {
            toCoupon(
                it
            )
        })),
        UsedCouponBook(CouponBook(entity.usedCouponBook.map {
            toCoupon(
                it
            )
        })),
        RoyalCustomers(),
        entity.id!!,
    )
}

internal fun fromShop(shop: Shop): ShopEntity {
    return ShopEntity(
        shop.showPublishedCoupons()
            .map { fromCoupon(it) },
        shop.showEventCoupons()
            .map { fromCoupon(it) },
        shop.showHandOutCoupon()
            .map { fromCoupon(it) },
        shop.showUsedCoupons()
            .map { fromCoupon(it) },
        shop.id,
    )
}
