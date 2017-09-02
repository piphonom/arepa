package org.piphonom.arepa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * Created by piphonom
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    @ResponseBody
    ResponseEntity<ExceptionRepresentation> handle(Exception exception) {
        ExceptionRepresentation body = new ExceptionRepresentation(exception.getLocalizedMessage());
        HttpStatus responseStatus = resolveAnnotatedResponseStatus(exception);
        return new ResponseEntity<>(body, responseStatus);
    }

    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private class ExceptionRepresentation {
        private String message;

        public ExceptionRepresentation(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
