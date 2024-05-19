package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.member.Member

class GiftCouponByMessageService(
    loadMemberStatePort: LoadMemberStatePort,
    createGiftCouponMessageStatePort: CreateGiftCouponMessageStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    private val transactionArea: TransactionArea,
    private val findMember: (Long) -> Member = { loadMemberStatePort.findMember(it) },
    private val createGiftMessage: (Member, Coupon, String) -> GiftMessage = { sender, coupon, message ->
        createGiftCouponMessageStatePort.create(sender, coupon, message)
    },
    private val updateMembersCoupon: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) }
) : GiftCouponByMessageUseCase {
    override fun sendGiftAvailableCoupon(memberId: Long, couponId: Long, giftMessage: String): GiftMessage {
        return transactionArea.run {
            val sender = findMember(memberId)
            val coupon = sender.showMyCouponBook().find { it.id == couponId }
            if (coupon == null) {
                throw IllegalArgumentException("쿠폰이 존재하지 않습니다.")
            }
            sender.useCoupon(coupon)
            updateMembersCoupon(sender)
            createGiftMessage(sender, coupon, giftMessage)
        }
    }
}
