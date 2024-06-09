package com.example.estdelivery

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "batch.chunk")
data class ChunkSizeProperty(
    val commitSize: Int,
    val processSize: Int,
    val writeSize: Int,
)
