package com.example.estdelivery.client

interface MemberClient{
    fun findMemberById(memberId: Long): MemberResponse
}
