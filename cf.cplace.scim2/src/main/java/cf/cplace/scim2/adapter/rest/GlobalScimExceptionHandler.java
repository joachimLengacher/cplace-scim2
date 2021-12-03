package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.ApiError;
import cf.cplace.scim2.domain.ConflictException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;

@Order(-500)
@ControllerAdvice
public class GlobalScimExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    protected ResponseEntity<ApiError> handleGeneralExceptions(ConflictException ex) {
        return apiError(CONFLICT, ex.getMessage());
    }

    private ResponseEntity<ApiError> apiError(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiError(httpStatus.value(), message));
    }
}
