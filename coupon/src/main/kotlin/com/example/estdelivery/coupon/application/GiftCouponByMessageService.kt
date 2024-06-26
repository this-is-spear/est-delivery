package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.GiftCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.`in`.web.dto.GiftMessageResponse
import com.example.estdelivery.coupon.application.port.out.CreateGiftCouponMessageStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.ValidateGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.coupon.GiftMessage
import com.example.estdelivery.coupon.domain.member.Member
import java.net.URL

class GiftCouponByMessageService(
    loadMemberStatePort: LoadMemberStatePort,
    createGiftCouponMessageStatePort: CreateGiftCouponMessageStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    validateGiftCouponCodeStatePort: ValidateGiftCouponCodeStatePort,
    private val transactionArea: TransactionArea,
    private val findMember: (Long) -> Member = { loadMemberStatePort.findMember(it) },
    private val createGiftMessage: (Member, Coupon, String, GiftCouponCode) -> GiftMessage = { sender, coupon, message, code ->
        createGiftCouponMessageStatePort.create(sender, coupon, message, code)
    },
    private val validateCouponCode: (GiftCouponCode) -> Boolean = { validateGiftCouponCodeStatePort.validate(it) },
    private val updateMembersCoupon: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) }
) : GiftCouponByMessageUseCase {
    /**
     * 선물 메시지를 통해 쿠폰을 선물한다.
     *
     * 1. 보낼 사람의 정보를 조회한다.
     * 2. 보낼 쿠폰의 정보를 조회한다.
     * 3. 보낼 사람의 쿠폰북에서 쿠폰을 삭제한다.
     * 4. 등록할 쿠폰 코드를 검증한다.
     * 5. 선물 메시지를 생성한다.
     *
     * @param memberId 선물하는 회원 식별자
     * @param couponId 선물할 쿠폰 식별자
     * @param giftMessage 선물 메시지
     */
    override fun sendGiftAvailableCoupon(memberId: Long, couponId: Long, giftMessage: String): GiftMessageResponse {
        return transactionArea.run {
            val sender = findMember(memberId)
            val coupon = sender.showMyCouponBook().find { it.id == couponId }
            if (coupon == null) {
                throw IllegalArgumentException("쿠폰이 존재하지 않습니다.")
            }
            val couponCode: GiftCouponCode = getGiftCouponCode()
            sender.useCoupon(coupon)
            updateMembersCoupon(sender)
            createGiftMessage(sender, coupon, giftMessage, couponCode).let {
                GiftMessageResponse(
                    senderName = it.sender.name,
                    description = it.giftMessage,
                    enrollEndDate = it.giftCoupon.enrollEndDate,
                    enrollHref = URL("http", "localhost", 8080, "/gift-coupons/enroll/${it.giftCouponCode.code}")
                )
            }
        }
    }

    private fun getGiftCouponCode(): GiftCouponCode {
        lateinit var couponCode: GiftCouponCode
        do {
            couponCode = GiftCouponCode.create()
        } while (!validateCouponCode(couponCode))
        return couponCode
    }
}
