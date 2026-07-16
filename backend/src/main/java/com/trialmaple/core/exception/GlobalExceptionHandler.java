package com.trialmaple.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoDailyMapFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoDailyMapFound(NoDailyMapFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }

    @ExceptionHandler(InvalidMapException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidMap(InvalidMapException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }
    
    @ExceptionHandler(InvalidGameModeException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidGameMode(InvalidGameModeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }

    @ExceptionHandler(InvalidAttemptException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidAttemptNumber(InvalidAttemptException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }

    @ExceptionHandler(DiscordAuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleDiscordAuthentication(DiscordAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }

    @ExceptionHandler(BackofficeAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleBackofficeAccessDenied(BackofficeAccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        Instant.now(),
                        ex.getCode().name(),
                        ex.getMessage()));
    }
}
