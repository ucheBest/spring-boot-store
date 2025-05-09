package com.codewithmosh.store.common;

import com.codewithmosh.store.auth.UnAuthorisedException;
import com.codewithmosh.store.carts.CartIsEmptyException;
import com.codewithmosh.store.carts.CartNotFoundException;
import com.codewithmosh.store.orders.OrderNotFoundException;
import com.codewithmosh.store.users.BadUserRequestException;
import com.codewithmosh.store.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
        MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnreadableMessage() {
        return ResponseEntity.badRequest().body(
            new ErrorDto("Invalid request body"));
    }

    @ExceptionHandler({
        CartIsEmptyException.class,
        CartNotFoundException.class,
        UserNotFoundException.class,
        OrderNotFoundException.class
    })
    public ResponseEntity<ErrorDto> handleNotFoundExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(UnAuthorisedException.class)
    public ResponseEntity<ErrorDto> handleUnauthorisedMessage(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorDto(ex.getMessage())
        );
    }

    @ExceptionHandler(BadUserRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequestMessage(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorDto(ex.getMessage())
        );
    }
}
