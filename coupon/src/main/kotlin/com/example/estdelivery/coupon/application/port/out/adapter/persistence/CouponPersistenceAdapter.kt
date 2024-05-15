package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.CreateCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadCouponStatePort
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.CouponRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class CouponPersistenceAdapter(
    private val couponRepository: CouponRepository,
) : CreateCouponStatePort,
    LoadCouponStatePort {
    @Transactional
    override fun create(coupon: Coupon): Coupon {
        return toCoupon(
            couponRepository.save(
                fromCoupon(
                    coupon
                )
            )
        )
    }

    override fun exists(couponId: Long): Boolean {
        return couponRepository.existsById(couponId)
    }

    override fun findById(couponId: Long): Coupon {
        val couponEntity = couponRepository.findById(couponId)
        require(couponEntity.isPresent) { "존재하지 않는 쿠폰입니다." }
        return toCoupon(couponEntity.get())
    }
}
