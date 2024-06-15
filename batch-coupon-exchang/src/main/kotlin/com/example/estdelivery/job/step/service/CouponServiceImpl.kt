package com.example.estdelivery.job.step.service

import com.example.estdelivery.domain.Coupon
import com.example.estdelivery.domain.CouponStateAmountType
import com.example.estdelivery.domain.CouponStateType
import com.example.estdelivery.job.step.service.repository.CouponRepository
import org.springframework.stereotype.Service

@Service
class CouponServiceImpl(
    private val couponRepository: CouponRepository
) : CouponService {
    override fun createCouponToBeExchange(
        name: String,
        description: String,
        amountType: CouponStateAmountType,
        amount: Int
    ): Long {
        require(name.isNotBlank()) { "Name must not be blank" }
        require(description.isNotBlank()) { "Description must not be blank" }
        require(amount > 0) { "Amount must be greater than 0" }

        val id = couponRepository.save(
            Coupon(
                name = name,
                description = description,
                amountType = amountType,
                type = CouponStateType.REWARD,
                amount = amount
            )
        ).id

        check(id != 0L) { "Coupon creation failed" }
        return id
    }
}
