package cz.cvut.fel.pivchart.exceptions.api

import org.springframework.http.HttpStatus

class NotFoundException(
    reason: String
) : RestException (
    reason = "Entity not found.",
    httpStatus = HttpStatus.NOT_FOUND
)