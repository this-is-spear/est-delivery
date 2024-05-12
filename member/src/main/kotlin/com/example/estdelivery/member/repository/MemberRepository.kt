package com.example.estdelivery.member.repository

import com.example.estdelivery.member.entiry.Member
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>