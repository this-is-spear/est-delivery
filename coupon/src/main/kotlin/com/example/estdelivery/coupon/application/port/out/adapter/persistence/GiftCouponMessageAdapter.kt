package com.example.estdelivery.coupon.application.port.out.adapter.persistence

import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.application.port.out.LoadGiftCouponStatePort
import com.example.estdelivery.coupon.application.port.out.UseGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.port.out.ValidateGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.entity.GiftMessageEntity
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.fromCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.mapper.toCoupon
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.EnrollDateRepository
import com.example.estdelivery.coupon.application.port.out.adapter.persistence.repository.GiftMessageRepository
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.member.Member
import jakarta.transaction.Transactional
import java.time.LocalDate
import org.springframework.stereotype.Component

@Component
class GiftCouponMessageAdapter(
    private val giftMessageRepository: GiftMessageRepository,
    private val enrollDateRepository: EnrollDateRepository,
) : CreateGiftCouponMessageStatePort, ValidateGiftCouponCodeStatePort, UseGiftCouponCodeStatePort,
    LoadGiftCouponStatePort {
    override fun create(sender: Member, coupon: Coupon, message: String, giftCouponCode: GiftCouponCode): GiftMessage {
        val enrollTerm = enrollDateRepository.findLatestPolicy()
            ?: throw IllegalStateException("EnrollTerm not found")

        val enrollDate = LocalDate.now()

        val giftMessageEntity = giftMessageRepository.save(
            GiftMessageEntity(
                sender = sender.id,
                coupon = fromCoupon(coupon),
                message = message,
                enrollDate = enrollDate,
                enrollEndDate = enrollDate.plus(enrollTerm.term, enrollTerm.unit),
                enrollCode = giftCouponCode.code
            )
        )
        return GiftMessage(
            sender = sender,
            giftMessage = giftMessageEntity.message,
            giftCouponCode = GiftCouponCode(giftMessageEntity.enrollCode),
            giftCoupon = GiftCoupon(coupon, giftMessageEntity.enrollEndDate, giftMessageEntity.isUsed)
        )
    }

    override fun validate(giftCouponCode: GiftCouponCode): Boolean {
        return giftMessageRepository.existsByEnrollCode(giftCouponCode.code)
    }

    @Transactional
    override fun useBy(giftCouponCode: GiftCouponCode) {
        val messageEntity = (giftMessageRepository.findByEnrollCode(giftCouponCode.code)
            ?: throw IllegalArgumentException("GiftCouponCode not found"))
        messageEntity.isUsed = true
    }

    override fun findGiftCoupon(giftCouponCode: GiftCouponCode): GiftCoupon {
        return giftMessageRepository.findByEnrollCode(giftCouponCode.code)
            ?.let { GiftCoupon(toCoupon(it.coupon), it.enrollEndDate, it.isUsed) }
            ?: throw IllegalArgumentException("GiftCouponCode not found")
    }
}
