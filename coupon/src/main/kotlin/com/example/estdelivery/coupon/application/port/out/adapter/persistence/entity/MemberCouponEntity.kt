package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class MemberCouponEntity(
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    val coupon: CouponEntity,
    val memberId: Long,
    @Enumerated(EnumType.STRING)
    var status: MemberCouponUseState,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemberEntity) return false

        if (other.id == 0L) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
