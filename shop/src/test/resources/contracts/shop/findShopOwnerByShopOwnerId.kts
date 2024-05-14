package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

listOf(
    contract {
        name = "get owner id is 1"
        request {
            method = GET
            url = url("/owners/1")
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "shopOwnerId" to 1,
                "shop" to mapOf(
                    "royalCustomers" to listOf(1, 2, 3),
                    "name" to "첫 번째 가게",
                    "id" to 1
                )
            )
        }
    },
    contract {
        name = "get owner id is 2"
        request {
            method = GET
            url = url("/owners/2")
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "shopOwnerId" to 2,
                "shop" to mapOf(
                    "royalCustomers" to emptyList<Int>(),
                    "name" to "두 번째 가게",
                    "id" to 2
                )
            )
        }
    }
)
