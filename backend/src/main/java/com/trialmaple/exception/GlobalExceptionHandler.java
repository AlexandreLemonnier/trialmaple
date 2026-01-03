package com.trialmaple.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.trialmaple.model.dto.ErrorResponseDto;

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

    @ExceptionHandler(InvalidMapNameException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidMapName(InvalidMapNameException ex) {
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
}
