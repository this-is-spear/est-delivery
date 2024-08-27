package com.example.estdelivery

import javax.sql.DataSource
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


/**
 * datasource 를 빈으로 등록하고 EnableBatchProcessing 애너테이션을 사용하면 순환 의존성으로 인해 datasource 를 외부에서 주입받기 어렵다.
 * DefaultBatchConfiguration 로 의존성을 주입하자.
 */
@Configuration
class BatchConfiguration(
    private val dataSource: DataSource,
    private val transactionManager: PlatformTransactionManager,
) : DefaultBatchConfiguration() {
    override fun getDataSource(): DataSource {
        return dataSource
    }

    override fun getTransactionManager(): PlatformTransactionManager {
        return transactionManager
    }
}
