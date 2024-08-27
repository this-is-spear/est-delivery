package com.example.estdelivery.client

import com.example.estdelivery.domain.PhoneNumber
import com.example.estdelivery.domain.UserName

data class MemberResponse(
    val phone: PhoneNumber,
    val name: UserName,
)
