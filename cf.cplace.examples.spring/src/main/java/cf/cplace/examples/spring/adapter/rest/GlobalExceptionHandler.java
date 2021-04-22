package cf.cplace.examples.spring.adapter.rest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.NotFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // prefer this over the default cplace exception handler
public class GlobalExceptionHandler {

    public static class Error {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Error> entityNotFoundHandler(NotFoundException notFoundException) {
        return createErrorResponse(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Error> forbiddenHandler(ForbiddenException forbiddenException) {
        return createErrorResponse(forbiddenException.getMessage(), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Error> createErrorResponse(String message, HttpStatus httpStatus) {
        Error error = new Error(message);
        return new ResponseEntity<>(error, httpStatus);
    }
}
