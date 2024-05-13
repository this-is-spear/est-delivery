
import org.springframework.cloud.contract.spec.ContractDsl.Companion.contract

contract {
    request {
        method = PUT
        url = url("/events/1/participants/1")
    }
    response {
        status = OK
    }
}
