package net.dreamlu.system.util;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
	private int status = 200;

	public BaseException() {
	}

	public BaseException(String message, int status) {
		super(message);
		this.status = status;
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
