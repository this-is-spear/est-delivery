package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "shop")
class ShopEntity(
    @ManyToMany(
        targetEntity = CouponEntity::class
    )
    @JoinTable(
        name = "publish_coupon_book",
        joinColumns = [JoinColumn(name = "shop_id")],
        inverseJoinColumns = [JoinColumn(name = "coupon_id")],
    )
    val publishedCouponBook: List<CouponEntity>,
    @ManyToMany(
        targetEntity = CouponEntity::class
    )
    @JoinTable(
        name = "publish_event_coupon_book",
        joinColumns = [JoinColumn(name = "shop_id")],
        inverseJoinColumns = [JoinColumn(name = "coupon_id")],
    )
    val publishedEventCouponBook: List<CouponEntity>,
    @ManyToMany(
        targetEntity = CouponEntity::class
    )
    @JoinTable(
        name = "handout_coupon_book",
        joinColumns = [JoinColumn(name = "shop_id")],
        inverseJoinColumns = [JoinColumn(name = "coupon_id")],
    )
    var handOutCouponBook: List<CouponEntity>,
    @ManyToMany(
        targetEntity = CouponEntity::class
    )
    @JoinTable(
        name = "use_coupon_book",
        joinColumns = [JoinColumn(name = "shop_id")],
        inverseJoinColumns = [JoinColumn(name = "coupon_id")],
    )
    var usedCouponBook: List<CouponEntity>,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShopEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
