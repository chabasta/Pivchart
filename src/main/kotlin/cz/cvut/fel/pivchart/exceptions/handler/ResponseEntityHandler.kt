package cz.cvut.fel.pivchart.exceptions.handler

import cz.cvut.fel.pivchart.exceptions.api.RestException
import cz.cvut.fel.pivchart.model.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ResponseEntityHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(RestException::class)])
    fun handleEntityNotFoundException(e: RestException): ResponseEntity<ErrorResponse> {
        val errorBody = ErrorResponse(
            message = e.reason,
        )
        return ResponseEntity(errorBody, e.httpStatus)
    }
}