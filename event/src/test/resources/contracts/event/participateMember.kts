import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

arrayOf(
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{0,7}[1-9]/participants/[0-9]{0,2}[1-9]")))
        }
        response {
            status = OK
        }
    },
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{0,7}[1-9]/participants/1000")))
        }
        response {
            status = BAD_REQUEST
        }
    },
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{0,7}[1-9]/participants/1001")))
        }
        response {
            status = TOO_MANY_REQUESTS
        }
    },
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{1,9}/participants/1100")))
        }
        response {
            status = INTERNAL_SERVER_ERROR
        }
    },
    contract {
        request {
            method = PUT
            url = url(v(regex("/events/[0-9]{1,9}/participants/1101")))
        }
        response {
            status = SERVICE_UNAVAILABLE
        }
    }
)
