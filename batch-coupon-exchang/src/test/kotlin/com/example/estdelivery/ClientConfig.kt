package com.example.estdelivery

import com.example.estdelivery.client.MemberClient
import com.example.estdelivery.fake.AlimTalkFakeClient
import com.example.estdelivery.fake.MemberFakeClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class ClientConfig {
    @Bean
    fun memberClient(): MemberClient = MemberFakeClient()

    @Bean
    fun alimTalkClient(): AlimTalkFakeClient = AlimTalkFakeClient()
}
