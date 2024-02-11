package com.portal26.webhook.app.exception;


import com.portal26.webhook.app.constants.WebhookContstants;
import com.portal26.webhook.app.pojo.ErrorModel;
import com.portal26.webhook.app.pojo.ErrorResponse;

import com.portal26.webhook.app.pojo.ErrorResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import javax.lang.model.type.ErrorType;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleWebhookException(Exception ex, WebRequest req) {
        log.error("Exception :"+ex.getMessage());
        ErrorResponse response = new ErrorResponse();
        response.setMessage(WebhookContstants.ERROR_MESSAGE);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParseException.class)
    protected ResponseEntity<Object> handleWebhookException(ParseException ex, WebRequest req) {
        log.error("ParseException :"+ex.getMessage());
        ErrorResponse response = new ErrorResponse();
        response.setMessage(WebhookContstants.DATE_FORMAT_ERROR);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleWebhookException(IOException ex, WebRequest req) {
        log.error("IOException :"+ex.getMessage());
        ErrorResponse response = new ErrorResponse();
        response.setMessage(WebhookContstants.ERROR_MESSAGE);
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleException(MethodArgumentNotValidException e) {
        List<ErrorModel> errorModels = processErrors(e);
        return ErrorResponseModel
                .builder()
                .errors(errorModels)
                .type("INVALID".toString())
                .build();
    }

    private List<ErrorModel> processErrors(MethodArgumentNotValidException e) {
        List<ErrorModel> validationErrorModels = new ArrayList<ErrorModel>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            ErrorModel validationErrorModel = ErrorModel
                    .builder()
                    .code(fieldError.getCode())
                    .source(fieldError.getObjectName() + "/" + fieldError.getField())
                    .detail(fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .build();
            validationErrorModels.add(validationErrorModel);
        }
        return validationErrorModels;
    }


}