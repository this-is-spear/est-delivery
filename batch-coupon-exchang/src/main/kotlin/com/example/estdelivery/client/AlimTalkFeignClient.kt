package com.example.estdelivery.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(
    name = "alim",
    url = "http://localhost:8090"
)
interface AlimTalkFeignClient : AlimTalkClient {
    @PostMapping("/alim")
    override fun sendAlimTalk(@RequestBody alimTalkRequest: AlimTalkRequest)
}
