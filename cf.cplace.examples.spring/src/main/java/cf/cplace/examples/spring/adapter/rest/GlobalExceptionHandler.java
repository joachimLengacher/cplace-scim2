package cf.cplace.examples.spring.adapter.rest;

import cf.cplace.examples.spring.domain.InvalidReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A global exception handler for all Spring controllers.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // prefer this over the default cplace exception handler
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static class Error {
        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @ExceptionHandler(InvalidReferenceException.class)
    protected ResponseEntity<Error> handle(InvalidReferenceException invalidReferenceException) {
        log.debug("Invalid reference", invalidReferenceException);
        return createErrorResponse(invalidReferenceException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Error> createErrorResponse(String message, HttpStatus httpStatus) {
        Error error = new Error(message);
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
