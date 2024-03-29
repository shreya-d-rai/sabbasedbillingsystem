package com.webtree.slabbasedbillingsystem.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.webtree.slabbasedbillingsystem.annotation.ExceptionInfo;
import com.webtree.slabbasedbillingsystem.exception.SlabBasedBillingException;
import com.webtree.slabbasedbillingsystem.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionInfo
	@ExceptionHandler(SlabBasedBillingException.class)
	public ResponseEntity<ErrorResponse> productException(SlabBasedBillingException ex){
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.errorMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		
	}
	
	@ExceptionInfo
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = (( FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		}
		);
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.errorMessage(errors.toString()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		
	}
	
	@ExceptionInfo
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> pageNotFoundException(Exception ex){
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.NOT_FOUND.value())
				.errorMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		
	}
	
	@ExceptionInfo
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> generalException(Exception ex){
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.errorMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		
	}
	
	@ExceptionInfo
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> authorizationException(AccessDeniedException ex){
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.FORBIDDEN.value())
				.errorMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		
	}
	
	@ExceptionInfo
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException ex){
		ErrorResponse errorResponse = ErrorResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
				.errorMessage(ex.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		
	}

}
