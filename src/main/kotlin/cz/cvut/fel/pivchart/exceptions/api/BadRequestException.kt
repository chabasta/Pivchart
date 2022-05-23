package cz.cvut.fel.pivchart.exceptions.api

import org.springframework.http.HttpStatus

class BadRequestException(reason: String
) : RestException(
    reason = reason,
    httpStatus = HttpStatus.BAD_REQUEST
)