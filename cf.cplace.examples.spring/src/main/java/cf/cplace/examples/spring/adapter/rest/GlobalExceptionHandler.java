package cf.cplace.examples.spring.adapter.rest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * A global exception handler for all Spring controllers.
 */
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

//    @ExceptionHandler(MyCustomPluginException.class)
//    protected ResponseEntity<Error> handle(MyCustomPluginException notFoundException) {
//        return createErrorResponse(notFoundException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR /* chose your error code here*/);
//    }

//    @ExceptionHandler(AnotherCustomPluginException.class)
//    protected ResponseEntity<Error> handle(AnotherCustomPluginException notFoundException) {
//        return createErrorResponse(notFoundException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR /* chose another error code here*/);
//    }

    private ResponseEntity<Error> createErrorResponse(String message, HttpStatus httpStatus) {
        Error error = new Error(message);
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
