package com.example.estdelivery.job.step.writer

import com.example.estdelivery.client.AlimTalkClient
import com.example.estdelivery.client.AlimTalkRequest
import com.example.estdelivery.domain.CouponExchangeNotificationTemplate
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NotificationWriters {
    @Bean
    fun notificationWriter(
        alimTalkClient: AlimTalkClient,
    ) = ItemWriter<CouponExchangeNotificationTemplate> {
        it.forEach { notification ->
            alimTalkClient.sendAlimTalk(
                AlimTalkRequest(
                    sender = notification.sender,
                    message = notification.sendMessage,
                )
            )
        }
    }
}
