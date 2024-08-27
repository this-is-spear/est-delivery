package com.example.estdelivery.fake

import com.example.estdelivery.client.AlimTalkClient
import com.example.estdelivery.client.AlimTalkRequest
import org.springframework.stereotype.Component

@Component
class AlimTalkFakeClient: AlimTalkClient {
    override fun sendAlimTalk(alimTalkRequest: AlimTalkRequest) {
        println(alimTalkRequest)
    }
}
