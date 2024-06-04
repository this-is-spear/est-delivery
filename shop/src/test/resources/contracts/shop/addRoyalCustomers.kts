package shop

import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract


arrayOf(
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{0,7}[1-9]/royal-customers/[0-9]{0,2}[1-9]")))
        }
        response {
            status = OK
        }
    },
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{0,7}[1-9]/royal-customers/1000")))
        }
        response {
            status = BAD_REQUEST
        }
    },
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{0,7}[1-9]/royal-customers/1001")))
        }
        response {
            status = TOO_MANY_REQUESTS
        }
    },
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{0,7}[1-9]/royal-customers/1100")))
        }
        response {
            status = INTERNAL_SERVER_ERROR
        }
    },
    contract {
        request {
            method = POST
            url = url(v(regex("/owners/shop/[0-9]{0,7}[1-9]/royal-customers/1101")))
        }
        response {
            status = SERVICE_UNAVAILABLE
        }
    }
)
