package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class MemberEntity(
    @ManyToMany(
        targetEntity = CouponEntity::class,
    )
    @JoinTable(
        name = "unused_coupon_book",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "coupon_id")],
    )
    var unusedCoupons: List<CouponEntity>,
    @Id
    @Column(name = "member_id")
    val id: Long,
)
