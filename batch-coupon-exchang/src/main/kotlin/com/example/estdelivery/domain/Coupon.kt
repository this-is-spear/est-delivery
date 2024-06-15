package com.example.estdelivery.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.NamedQuery
import jakarta.persistence.Table

@Entity
@Table(name = "coupon")
@NamedQuery(
    name = "couponFinaAll",
    query = "SELECT c FROM Coupon c"
)
class Coupon(
    val name: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val amountType: CouponStateAmountType,
    @Enumerated(EnumType.STRING)
    val type: CouponStateType,
    val amount: Int,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Coupon) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
