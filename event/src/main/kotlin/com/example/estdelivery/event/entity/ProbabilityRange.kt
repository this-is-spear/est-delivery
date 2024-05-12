package com.example.estdelivery.event.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ProbabilityRange(
    val min: Int,
    val max: Int,
    val probability: Double,
    @ManyToOne
    @JoinColumn(name = "event_id")
    val event: Event? = null,
    @Id
    @GeneratedValue
    val id: Long? = null,
) {
    init {
        require(min < max) { "min 은 max보다 작아야 합니다." }
        require(min >= 0) { "min은 0보다 작을 수 없습니다." }
        require(0.0 < probability && probability < 1.0) { "probability는 0보다 커야 하고 1보다 작아야 합니다." }
    }
}
