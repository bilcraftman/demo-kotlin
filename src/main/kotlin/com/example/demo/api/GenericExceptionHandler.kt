package com.example.demo.api;

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import tech.becoming.common.exceptions.AbstractRuntimeException
import java.time.Instant

@ControllerAdvice
class GenericExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(AbstractRuntimeException::class)])
    fun handleConflict(ex: AbstractRuntimeException, request: WebRequest): ResponseEntity<Any> {

        val details = ErrorBody(
                timestamp = Instant.now(),
                status = ex.httpCode,
                error = ex.message,
                message = ex.toString(),
                path = ((request as ServletWebRequest).request.requestURI)
                )

        var status = HttpStatus.resolve(details.status)
        status = status ?: HttpStatus.I_AM_A_TEAPOT

        return handleExceptionInternal(ex, details, HttpHeaders(), status, request)
    }
}

/**
 *  Example of a response body
 *  {
 *      "timestamp": "2021-04-03T18:41:16.587+00:00",
 *      "status": 500,
 *      "error": "Internal Server Error",
 *      "message": "",
 *      "path": "/robots/2"
 *  }
 */
internal class ErrorBody (
    val timestamp: Instant = Instant.now(),
    val status: Int = 500,
    val error: String? = "Internal Server Error",
    val message: String? = "",
    val path: String = ""
)