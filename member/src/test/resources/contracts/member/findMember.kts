package member

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = GET
            url = url(v(regex("/members/[0-9]{0,2}[1-9]")))
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
            url = url(v(regex("/members/1000")))
        }
        response {
            status = BAD_REQUEST
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/members/1001")))
        }
        response {
            status = TOO_MANY_REQUESTS
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/members/1100")))
        }
        response {
            status = INTERNAL_SERVER_ERROR
        }
    },
    contract {
        request {
            method = GET
            url = url(v(regex("/members/1101")))
        }
        response {
            status = SERVICE_UNAVAILABLE
        }
    }
)
