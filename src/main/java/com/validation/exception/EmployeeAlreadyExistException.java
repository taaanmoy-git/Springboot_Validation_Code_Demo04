package com.validation.exception;

import org.springframework.http.HttpStatus;

public class EmployeeAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID= 1L;
	private HttpStatus status;
	
	public EmployeeAlreadyExistException(String msg) {
		super(msg);
	}
	public EmployeeAlreadyExistException(String msg, Throwable cause) {
		super(msg,cause);
	}
	// Constructor with message, cause, and HttpStatus
    public EmployeeAlreadyExistException(String msg, Throwable cause, HttpStatus status) {
        super(msg, cause);
        this.status = status;
    }
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
