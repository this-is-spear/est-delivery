package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
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
    //TODO 쿠폰 도메인에서 회원 정보는 Long 으로 관리
    @ElementCollection
    var royalCustomers: List<Long>,
    var name: String,
    @Id
    @Column(name = "shop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
