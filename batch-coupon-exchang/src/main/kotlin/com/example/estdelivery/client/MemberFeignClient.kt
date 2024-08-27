package com.example.estdelivery.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "member",
    url = "http://localhost:8081"
)
interface MemberFeignClient : MemberClient {
    @GetMapping("/members/{memberId}")
    override fun findMemberById(@PathVariable memberId: Long): MemberResponse
}
