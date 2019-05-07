package com.nixsolutions.exception;

public class UserSoapFail extends Exception {

	public UserSoapFail() {
		super();
	}

	public UserSoapFail(String message) {
		super(message);
	}

	public UserSoapFail(String message, Throwable cause) {
		super(message, cause);
	}

}
