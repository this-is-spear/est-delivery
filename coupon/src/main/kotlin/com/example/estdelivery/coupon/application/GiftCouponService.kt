package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.GiftCouponUseCase
import com.example.estdelivery.coupon.application.port.`in`.command.GiftCouponCommand
import com.example.estdelivery.coupon.application.port.out.LoadCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.Coupon
import com.example.estdelivery.coupon.domain.member.Member
import org.springframework.stereotype.Service

@Service
class GiftCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    private val transactionArea: TransactionArea,
) : GiftCouponUseCase {
    /**
     * 선물 할 쿠폰과 선물 할 회원의 식별자를 입력해 쿠폰을 나눠준다.
     *
     * 1. 식별자로 회원을 조회한다.
     * 2. 선물 할 쿠폰을 조회한다.
     * 3. 선물받을 회원 쿠폰북에 해당 쿠폰을 추가한다.
     * 4. 선물한 회원 쿠폰북에 해당 쿠폰을 삭제한다.
     *
     * @param giftCouponCommand 선물할 쿠폰 정보와 선물할 회원 정보
     */
    override fun giftCoupon(giftCouponCommand: GiftCouponCommand) {
        val receiver = getReceiver(giftCouponCommand)

        transactionArea.run {
            val sender = getSender(giftCouponCommand)

            if (sender == receiver) {
                throw IllegalArgumentException("자기 자신에게 쿠폰을 선물할 수 없습니다.")
            }

            val coupon = getCoupon(giftCouponCommand)
            sender.sendCoupon(coupon, receiver)
            updateMember(receiver)
            updateMember(sender)
        }
    }

    private val getReceiver: (GiftCouponCommand) -> Member = {
        loadMemberStatePort.findMember(it.receiverId)
    }
    private val getSender: (GiftCouponCommand) -> Member = {
        loadMemberStatePort.findMember(it.senderId)
    }
    private val getCoupon: (GiftCouponCommand) -> Coupon = {
        loadCouponStatePort.findById(
            it.couponId
        )
    }
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) }
}
