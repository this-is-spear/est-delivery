package com.example.estdelivery.application

import com.example.estdelivery.application.port.`in`.GiftCouponUseCase
import com.example.estdelivery.application.port.`in`.command.GiftCouponCommand
import com.example.estdelivery.application.port.out.LoadCouponStatePort
import com.example.estdelivery.application.port.out.LoadMemberStatePort
import com.example.estdelivery.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.application.port.out.state.MemberState
import com.example.estdelivery.application.utils.TransactionArea
import com.example.estdelivery.domain.coupon.Coupon
import com.example.estdelivery.domain.member.Member

class GiftCouponService(
    loadMemberStatePort: LoadMemberStatePort,
    loadCouponStatePort: LoadCouponStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    private val transactionArea: TransactionArea,
    private val getReceiver: (GiftCouponCommand) -> Member = { loadMemberStatePort.findById(it.receiverId).toMember() },
    private val getSender: (GiftCouponCommand) -> Member = { loadMemberStatePort.findById(it.senderId).toMember() },
    private val getCoupon: (GiftCouponCommand) -> Coupon = { loadCouponStatePort.findByCouponId(it.couponId).toCoupon() },
    private val updateMember: (Member) -> Unit = { updateMemberStatePort.update(MemberState.from(it)) },
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
}