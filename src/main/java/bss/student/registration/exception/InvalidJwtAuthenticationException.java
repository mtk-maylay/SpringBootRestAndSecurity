package bss.student.registration.exception;

import io.jsonwebtoken.JwtException;

public class InvalidJwtAuthenticationException extends JwtException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJwtAuthenticationException(String message) {
		super(message);
	}

}
