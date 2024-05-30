package member

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("\\/members\\/[0-9]{1,7}[13579]")))
        }
        response {
            status = OK
            headers {
                contentType = "application/json"
            }
            body = body(
                "id" to value("${fromRequest().path(1)}"),
                "name" to "이건창"
            )
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("\\/members\\/[0-9]{1,7}[02468]")))
        }
        response {
            status = BAD_REQUEST
        }
    }
)
