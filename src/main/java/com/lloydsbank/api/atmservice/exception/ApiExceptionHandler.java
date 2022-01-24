package com.lloydsbank.api.atmservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The type Api exception handler.
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle response status exception .
     *
     * @param response the response
     * @param ex       the ex
     * @throws IOException the io exception
     */
    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusEx(HttpServletResponse response,ResponseStatusException ex) throws IOException {
        response.sendError(ex.getRawStatusCode(),ex.getMessage());
    }

    /**
     * Constraint violation exception.
     *
     * @param response the response
     * @throws IOException the io exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Handle http client exceptions.
     *
     * @param response the response
     * @param ex       the ex
     * @throws IOException the io exception
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public void handleHttpClientExceptions(HttpServletResponse response,HttpClientErrorException ex) throws IOException {
        log.error("Http Exception Occurred with response code {}", ex.getStatusCode(), ex);
        response.sendError(ex.getStatusCode().value(),ex.getStatusText());
    }

    /**
     * Handle http server exceptions.
     *
     * @param response the response
     * @param ex       the ex
     * @throws IOException the io exception
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public void handleHttpServerExceptions(HttpServletResponse response,HttpServerErrorException ex) throws IOException {
        log.error("Http Exception Occurred with response code {}", ex.getStatusCode(), ex);
        response.sendError(ex.getStatusCode().value(),ex.getStatusText());
    }

    /**
     * Handle time out exceptions.
     *
     * @param response the response
     * @param ex       the ex
     * @throws IOException the io exception
     */
    @ExceptionHandler(ResourceAccessException.class)
    public void handleTimeOutExceptions(HttpServletResponse response,ResourceAccessException ex) throws IOException {
        response.sendError(HttpStatus.REQUEST_TIMEOUT.value(),ex.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handle generic exceptions.
     *
     * @param response the response
     * @param ex       the ex
     * @throws IOException the io exception
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public void handleGenericExceptions(HttpServletResponse response,Exception ex) throws IOException {
        log.error("Exception Occurred ", ex);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Unknown Internal Server Error Occurred");
    }

}
