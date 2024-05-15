package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class UnusedCouponEntity(
    val memberId: Long,
    @OneToOne
    @JoinColumn(name = "coupon_id")
    val coupon: CouponEntity,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)
