package com.example.estdelivery.fake

import com.example.estdelivery.client.MemberClient
import com.example.estdelivery.client.MemberResponse
import com.example.estdelivery.domain.PhoneNumber
import com.example.estdelivery.domain.UserName
import org.springframework.stereotype.Component

@Component
class MemberFakeClient : MemberClient {
    override fun findMemberById(memberId: Long): MemberResponse {
        return MemberResponse(
            phone = PhoneNumber(
                // 임의 전화 번호 11 자리 추출한다. 시작은 010으로 시작한다.
                "010" + (1000..9999).random() + (1000..9999).random()
            ),
            name = UserName("홍길동"),
        )
    }
}
