package cz.cvut.fel.pivchart.exceptions.api

import org.springframework.http.HttpStatus

class ForbiddenException(
    reason: String
) : RestException (
    reason = reason,
    httpStatus = HttpStatus.FORBIDDEN
)