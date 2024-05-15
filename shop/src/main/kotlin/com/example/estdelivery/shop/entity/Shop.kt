package com.example.estdelivery.shop.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "shop")
class Shop(
    @OneToMany(
        targetEntity = RoyalCustomer::class,
        cascade = [CascadeType.ALL],
    )
    @JoinColumn(name = "shop_id")
    var royalCustomers: List<RoyalCustomer>,
    val name: String,
    @Id
    @Column(name = "shop_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
