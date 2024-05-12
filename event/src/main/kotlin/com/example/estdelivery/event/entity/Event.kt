package com.example.estdelivery.event.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Event(
    val description: String,
    val isProgress: Boolean,
    val disCountType: EventDiscountType,
    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL], orphanRemoval = true)
    val intervalsProbability: List<ProbabilityRange>,
    val disCountRangeMin: Int,
    val disCountRangeMax: Int,
    @ElementCollection
    @CollectionTable(
        name = "event_member",
        joinColumns = [JoinColumn(name = "event_id")]
    )
    @Column(name = "member_id")
    var participatedMembers: List<Long> = emptyList(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
)
