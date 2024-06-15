package com.example.estdelivery.domain

data class UserName(
    val name: String
) {
    init {
        require(name.isNotBlank()) { "이름은 비어있을 수 없습니다." }
    }
}
