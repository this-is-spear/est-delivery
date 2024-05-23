package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "gift_message")
class GiftMessageEntity(
    val message: String,
    @Column(name = "sender_id", nullable = false)
    val sender: Long,
    @Column(unique = true, nullable = false)
    val enrollCode: String,
    @OneToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    val coupon: CouponEntity,
    @Column(nullable = false)
    val enrollDate: LocalDate = LocalDate.now(),
    @Column(nullable = false)
    val enrollEndDate: LocalDate = enrollDate.plusMonths(6),
    var isUsed: Boolean = false,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GiftMessageEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
