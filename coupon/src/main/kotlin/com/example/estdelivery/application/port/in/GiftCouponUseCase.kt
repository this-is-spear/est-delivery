package com.example.estdelivery.application.port.`in`

import com.example.estdelivery.application.port.`in`.command.GiftCouponCommand

interface GiftCouponUseCase {
    /**
     * 선물 할 쿠폰과 선물 할 회원의 식별자를 입력해 쿠폰을 나눠준다.
     *
     * 1. 식별자로 회원을 조회한다.
     * 2. 선물 할 쿠폰을 조회한다.
     * 3. 선물받을 회원 쿠폰북에 해당 쿠폰을 추가한다.
     * 4. 선물한 회원 쿠폰북에 해당 쿠폰을 삭제한다.
     *
     * 검증 조건
     * - 회원이 검색되지 않으면 쿠폰을 선물할 수 없다.
     * - 쿠폰이 존재해야 하고 사용하지 않은 쿠폰이어야 한다.
     * - 동일한 회원이어서는 안된다.
     *
     * @param giftCouponCommand 선물할 쿠폰 정보와 선물할 회원 정보
     */
    fun giftCoupon(giftCouponCommand: GiftCouponCommand)
}
