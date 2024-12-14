package com.example.demo.infrastructure.common;

import com.example.demo.infrastructure.common.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequests(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseMessage message = getMessage(status, ex);

        log.error("Bad Request: {}", message.getMessage());
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNotFoundRequests(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseMessage message = getMessage(status, ex);

        log.error("Not Found Request: {}", message.getMessage());
        return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
    }


    private ResponseMessage getMessage(HttpStatus status, Exception ex) {
        final String message = ex.getMessage() == null ? ex.getClass().getSimpleName() : ex.getMessage();
        return new ResponseMessage(status.value(), message);
    }

    @AllArgsConstructor
    @Getter
    private static final class ResponseMessage {
        private final Date timestamp = new Date();
        private final int status;
        private final String message;
    }
}
