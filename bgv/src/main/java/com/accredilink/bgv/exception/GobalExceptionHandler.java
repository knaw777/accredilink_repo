package com.accredilink.bgv.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class GobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleException(CustomException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getExceptionMessge());
		errorResponse.setTimeStamp(LocalDateTime.now());
		errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

}
