package com.example.estdelivery.coupon.application.port.`in`

import com.example.estdelivery.coupon.application.port.`in`.command.IssueEventCouponCommand

interface IssueEventCouponUseCase {
    /**
     * 회원은 가게에 이벤트 쿠폰을 발행한다.
     * @param issueEventCouponCommand
     */
    fun issueEventCoupon(issueEventCouponCommand: IssueEventCouponCommand)
}
