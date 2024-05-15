package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "shop_owner")
class ShopOwnerEntity(
    @OneToOne(
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    @JoinColumn(name = "shop_id")
    var shopEntity: ShopEntity,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShopOwnerEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
