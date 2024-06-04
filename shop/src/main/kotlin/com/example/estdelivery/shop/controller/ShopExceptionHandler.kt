package com.example.estdelivery.shop.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class EventExceptionHandler {
    @ExceptionHandler(BadRequestException::class)
    fun handleIllegalArgumentException(e: BadRequestException): ResponseEntity<Void> {
        return ResponseEntity.badRequest().build()
    }

    @ExceptionHandler(InternalServerErrorException::class)
    fun handleIllegalStateException(e: InternalServerErrorException): ResponseEntity<Void> {
        return ResponseEntity.internalServerError().build()
    }

    @ExceptionHandler(TooManyRequestsException::class)
    fun handleTooManyRequests(e: TooManyRequestsException): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build()
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    fun handleServiceUnavailable(e: ServiceUnavailableException): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
    }
}

class BadRequestException : RuntimeException()
class InternalServerErrorException : RuntimeException()
class TooManyRequestsException : RuntimeException()
class ServiceUnavailableException : RuntimeException()
