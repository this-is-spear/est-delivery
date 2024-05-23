package com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "enroll_term")
class EnrollTermEntity(
    @Column(updatable = false)
    val term: Long,
    @Enumerated(value = EnumType.STRING)
    val unit: ChronoUnit,
    @Column(updatable = false)
    val createDate: LocalDateTime = LocalDateTime.now(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
