package com.ankamma.user.application.exception;

public class ExceptionResponse {

	private String message;
	private String errorCode;

	private String details;

	public ExceptionResponse(String message, String errorCode, String details) {
		super();

		this.message = message;
		this.errorCode = errorCode;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getDetails() {
		return details;
	}

}
