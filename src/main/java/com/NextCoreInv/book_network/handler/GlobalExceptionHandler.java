package com.NextCoreInv.book_network.handler;

import com.NextCoreInv.book_network.Exception.OperationNotPermittedExeption;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;


import static com.NextCoreInv.book_network.handler.BusinessErrorCodes.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_DISABLED.getCode())
                                .businessErrorDescription(ACCOUNT_DISABLED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error("Login and / or Password is incorrect")
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

//    @ExceptionHandler(ActivationTokenException.class)
//    public ResponseEntity<ExceptionResponse> handleException(ActivationTokenException exp) {
//        return ResponseEntity
//                .status(BAD_REQUEST)
//                .body(
//                        ExceptionResponse.builder()
//                                .error(exp.getMessage())
//                                .build()
//                );
//    }
//
    @ExceptionHandler(OperationNotPermittedExeption.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedExeption exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException exp) {
        // Handle specific runtime exceptions that should return 404
        if (exp.getMessage() != null && 
            (exp.getMessage().contains("No book found") || 
             exp.getMessage().contains("not found") ||
             exp.getMessage().contains("Not found"))) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorDescription("Resource not found")
                                    .error(exp.getMessage())
                                    .build()
                    );
        }
        
        // Handle authentication-related null pointer exceptions
        if (exp.getMessage() != null && 
            exp.getMessage().contains("Cannot invoke") && 
            exp.getMessage().contains("Authentication")) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorDescription("Authentication required")
                                    .error("You must be logged in to access this resource")
                                    .build()
                    );
        }
        
        // Default to internal server error for other runtime exceptions
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Invalid request parameter")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException exp) {
        // Check if it's authentication related
        if (exp.getMessage() != null && exp.getMessage().contains("connectedUser")) {
            return ResponseEntity
                    .status(UNAUTHORIZED)
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorDescription("Authentication required")
                                    .error("You must be logged in to access this resource")
                                    .build()
                    );
        }
        
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error("An unexpected error occurred")
                                .build()
                );
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException exp) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Malformed JSON request")
                                .error("Please check your JSON syntax and try again")
                                .build()
                );
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException exp) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.FORBIDDEN)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Access forbidden")
                                .error("You don't have permission to access this resource")
                                .build()
                );
    }

    @ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotSupportedException(
            org.springframework.web.HttpRequestMethodNotSupportedException exp) {
        return ResponseEntity
                .status(org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Method not allowed")
                                .error("HTTP method " + exp.getMethod() + " is not supported for this endpoint")
                                .build()
                );
    }

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(
            jakarta.persistence.EntityNotFoundException exp) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Resource not found")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }
}