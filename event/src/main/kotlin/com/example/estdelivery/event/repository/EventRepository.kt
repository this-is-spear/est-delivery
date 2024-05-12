package com.example.estdelivery.event.repository

import com.example.estdelivery.event.entity.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository: JpaRepository<Event, Long>