package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.services.exceptions.*;
import cf.cplace.scim2.domain.ConflictException;
import com.unboundid.scim2.common.messages.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(-500) // higher than cplace's default
@ControllerAdvice
public class GlobalScimExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex) {
        return apiError(INTERNAL_SERVER_ERROR, "Unexpected internal server error"); // do not reveal details
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return apiError(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ProtectedActionException.class, ProtectedEntityException.class, ReadOnlyModeException.class})
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleUnauthorized(CplaceException ex) {
        return apiError(UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleGeneralExceptions(ConflictException ex) {
        return apiError(CONFLICT, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> apiError(HttpStatus httpStatus, String message) {
        ErrorResponse error = new ErrorResponse(httpStatus.value());
        error.setDetail(message);
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
