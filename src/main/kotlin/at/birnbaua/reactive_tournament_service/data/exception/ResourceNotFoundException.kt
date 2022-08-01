package at.birnbaua.reactive_tournament_service.data.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource not found in database.")
class ResourceNotFoundException(msg: String) : RuntimeException(msg)