package com.example.estdelivery.domain

data class CouponExchangeNotificationTemplate(
    private val receivingPhoneNumber: PhoneNumber,
    private val receivingName: UserName,
    private val couponNameBeforeExchange: String,
    private val couponNameAfterExchange: String,
) {
    val sendMessage: String
        get(): String = """
            [이스트딜리버리]
            ${receivingName}님, 보관중인 쿠폰에 문제가 생겨 다음과 같이 변경됩니다.
            
            사유 : ${receivingName}님의 쿠폰이 만료되었습니다.
            변경 전 쿠폰 : $couponNameBeforeExchange
            변경 후 쿠폰 : $couponNameAfterExchange
            """.trimIndent()

    val sender: String
        get(): String = receivingPhoneNumber.number
}
