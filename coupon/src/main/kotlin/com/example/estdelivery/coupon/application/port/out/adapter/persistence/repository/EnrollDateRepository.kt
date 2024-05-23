package com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository

import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.EnrollTermEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface EnrollDateJpaRepository : JpaRepository<EnrollTermEntity, Long> {
    @Query("SELECT e FROM EnrollTermEntity e WHERE e.id = (SELECT MAX(e2.id) FROM EnrollTermEntity e2)")
    fun findLatestPolicy(): EnrollTermEntity?
}

@Repository
class EnrollDateRepository(
    private val repository: EnrollDateJpaRepository
) {
    fun findLatestPolicy(): EnrollTermEntity? {
        return repository.findLatestPolicy()
    }
}
