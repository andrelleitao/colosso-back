package com.ectoum.colosso.core.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ectoum.colosso.core.exception.model.ApiError;
import com.ectoum.colosso.core.exception.model.CustomMessageException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({ TransactionSystemException.class })
	protected ResponseEntity<Object> handlePersistenceException(final Exception ex, final WebRequest request) {
		Throwable cause = ((TransactionSystemException) ex).getRootCause();
		if (cause instanceof ConstraintViolationException) {

			ConstraintViolationException consEx = (ConstraintViolationException) cause;
			final List<String> errors = new ArrayList<String>();
			for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
				errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
			}

			final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, consEx.getLocalizedMessage(), errors);
			return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
		}

		final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				"error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({CustomMessageException.class})
	protected ResponseEntity<Object> handleCustomMessageException(final CustomMessageException ex, final WebRequest request) {
		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getErrors());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();        
        String message = null;
       
        if (mostSpecificCause != null) {
            // String exceptionName = mostSpecificCause.getClass().getName();
            message = mostSpecificCause.getMessage();
        } else {
        	message = ex.getMessage();            
        }
        
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, new ArrayList<String>());
        
        return new ResponseEntity<Object>(apiError, headers, status);
    }
	
}
