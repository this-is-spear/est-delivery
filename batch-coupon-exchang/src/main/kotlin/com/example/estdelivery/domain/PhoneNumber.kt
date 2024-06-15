package com.example.estdelivery.domain

data class PhoneNumber(
    val number: String
) {
    init {
        require(number.length == 11) { "휴대폰 번호는 11자리여야 합니다." }
    }
}
