package com.validation.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
//@RestControllerAdvice = @RestController + @ControllerAdvice
@RestControllerAdvice
public class GlobalEmployeeExceptionHandler {
	/* ErrorInfo data needed:
		private String errorMessage;
		private Integer errorCode;
		private LocalDateTime timestamp;
	 */
	//EmployeeNotFoundException happend it will return the data
	//It handle EmployeeNotFoundException exception
	@ExceptionHandler(value =EmployeeNotFoundException.class )
	public ResponseEntity<ErrorInfo> handleEmployeeNotFoundException(EmployeeNotFoundException ex){
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(ex.getMessage());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.NOT_FOUND);	
	}
	//EmployeeAlreadyExistException handler
	//Note Inp: EmployeeAlreadyExistException here we use getter and setter for HttpStatus status.
	//for that reason we can use ex.getStatus()
	public ResponseEntity<ErrorInfo> handleEmployeeAlreadyExistException(EmployeeAlreadyExistException ex){
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(ex.getMessage());
		error.setErrorCode(ex.getStatus().value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error,HttpStatus.CONFLICT);
	}
	// Global exception handler for all RuntimeExceptions
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorInfo> handleAllException(RuntimeException ex) {
	    // Creating an ErrorInfo object to store error details
	    ErrorInfo error = new ErrorInfo();
	    error.setErrorMessage(ex.getMessage()); // Setting the error message from the exception
	    error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()); // Setting the error code (500 - Internal Server Error)
	    error.setTimestamp(LocalDateTime.now()); // Adding a timestamp to the error
	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // Returning the error response with 500 status code
	}
	
	// ✅ Validation Error Handler (for @Valid & @Validated)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, String> validationErrors = new HashMap<>();

	    ex.getBindingResult().getAllErrors().forEach(
	    	  (error) -> {
	    			if (error instanceof FieldError fieldError) {
	    			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
	    		}
	    	}
	    );

	    ErrorInfo errorInfo = new ErrorInfo();
	    errorInfo.setErrorMessage("Validation failed:"+validationErrors.toString());
	    errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
	    errorInfo.setTimestamp(LocalDateTime.now());

	    return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	
}
