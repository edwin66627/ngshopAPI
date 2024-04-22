package com.ngshop.exception;

import com.ngshop.constant.ExceptionMessage;
import com.ngshop.entity.HttpResponse;
import com.ngshop.exception.domain.FileSizeNotAllowedException;
import com.ngshop.exception.domain.UnsupportedContentTypeException;
import com.ngshop.utils.ResponseUtility;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    public static final String ERROR_PATH = "/error";
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpResponse> handleInternalServerException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(ExceptionMessage.INTERNAL_SERVER_ERROR_MSG,INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value= METHOD_NOT_ALLOWED)
    public ResponseEntity<HttpResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return ResponseUtility.buildResponse(String.format(ExceptionMessage.METHOD_NOT_ALLOWED_MSG, supportedMethod),METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleNotFoundException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchFileException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleNoSuchFileException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleMaxUploadSizeExceededException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeNotAllowedException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleFileSizeNotAllowedException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedContentTypeException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleUnsupportedContentTypeException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> handleBadCredentialsException(Exception exception){
        return ResponseUtility.buildResponse(ExceptionMessage.INVALID_CREDENTIALS, BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<HttpResponse> handleNoHandlerFoundException(Exception exception){
        return ResponseUtility.buildResponse(exception.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(StripeException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleStripeException(Exception exception) {
        log.error(exception.getMessage());
        return ResponseUtility.buildResponse(exception.getMessage(),BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value= BAD_REQUEST)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return ResponseUtility.buildResponse(ExceptionMessage.NOT_FOUND_MSG,NOT_FOUND);
    }

}
