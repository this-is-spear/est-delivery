package com.example.estdelivery.coupon.application

import com.example.estdelivery.coupon.application.port.`in`.EnrollCouponByMessageUseCase
import com.example.estdelivery.coupon.application.port.out.LoadGiftCouponStatePort
import com.example.estdelivery.coupon.application.port.out.LoadMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UpdateMemberStatePort
import com.example.estdelivery.coupon.application.port.out.UseGiftCouponCodeStatePort
import com.example.estdelivery.coupon.application.utils.TransactionArea
import com.example.estdelivery.coupon.domain.coupon.GiftCoupon
import com.example.estdelivery.coupon.domain.coupon.GiftCouponCode
import com.example.estdelivery.coupon.domain.member.Member


class EnrollCouponByMessageService(
    loadMemberStatePort: LoadMemberStatePort,
    useGiftCouponCodeStatePort: UseGiftCouponCodeStatePort,
    loadGiftCouponStatePort: LoadGiftCouponStatePort,
    updateMemberStatePort: UpdateMemberStatePort,
    private val transactionArea: TransactionArea,
    private val findMember: (Long) -> Member = { loadMemberStatePort.findMember(it) },
    private val useGiftCouponCode: (GiftCouponCode) -> GiftCoupon = {
        useGiftCouponCodeStatePort.useBy(it)
        loadGiftCouponStatePort.findGiftCoupon(it)
    },
    private val updateMembersCoupon: (Member) -> Unit = { updateMemberStatePort.updateMembersCoupon(it) },
) : EnrollCouponByMessageUseCase {
    override fun enroll(memberId: Long, code: GiftCouponCode) {
        transactionArea.run {
            val member = findMember(memberId)
            val giftCoupon = useGiftCouponCode(code)
            member.receiveCoupon(giftCoupon.coupon)
            updateMembersCoupon(member)
        }
    }
}
