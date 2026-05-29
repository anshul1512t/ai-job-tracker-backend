package com.ai.job_tracker.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) { 
        return ResponseEntity.status(500).body(new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
        return ResponseEntity.status(400).body(new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(403).body(new ErrorResponse(exception.getMessage(), HttpStatus.FORBIDDEN));
    }
    
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("Token Expired", HttpStatus.UNAUTHORIZED));
    }
    
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception) {
        return ResponseEntity.status(401).body(new ErrorResponse("Invalid Token", HttpStatus.UNAUTHORIZED));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(400).body(new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(JobApplicationIdNotExist.class)
    public ResponseEntity<ErrorResponse> handleJobApplicationIdNotExist(JobApplicationIdNotExist exception){
        return ResponseEntity.status(404).body(new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxSize(
            MaxUploadSizeExceededException ex) {

        return ResponseEntity.status(400).body(new ErrorResponse("File size exceeds 10MB", HttpStatus.INSUFFICIENT_STORAGE));
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorResponse> handleEmptyFile(EmptyFileException ex){
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage(), HttpStatus.NO_CONTENT));
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormat(InvalidFileFormatException ex){
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE));
    }
}