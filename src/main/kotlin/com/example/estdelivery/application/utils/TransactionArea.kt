package com.example.estdelivery.application.utils

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class TransactionArea {
    @Transactional
    fun <T> run(supplier: () -> T): T {
        return supplier()
    }
}
