package com.logistics.logisticsCompany.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling validation errors in RESTful endpoints.
 * This class is responsible for intercepting MethodArgumentNotValidException
 * instances thrown during request processing and returning appropriate error responses.
 */
@RestControllerAdvice
public class ValidationExceptionHandler {
	/**
	 * Handles MethodArgumentNotValidException thrown during request processing
	 * and returns a map containing field-level validation errors.
	 *
	 * @param ex The MethodArgumentNotValidException instance representing the validation error.
	 * @return A map containing field names as keys and error messages as values.
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String,String> errorMap =new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			errorMap.put(error.getField(),error.getDefaultMessage());
		});
		return errorMap;
	}
}
