package cz.cvut.fel.pivchart.exceptions.api

import org.springframework.http.HttpStatus

abstract class RestException (
    val reason: String,
    val httpStatus: HttpStatus
) : RuntimeException()