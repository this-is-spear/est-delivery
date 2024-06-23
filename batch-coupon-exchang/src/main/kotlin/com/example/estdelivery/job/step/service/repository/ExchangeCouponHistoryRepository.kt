package com.example.estdelivery.job.step.service.repository

import com.example.estdelivery.domain.ExchangeCouponHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ExchangeCouponHistoryRepository: JpaRepository<ExchangeCouponHistory, Long>
