package compiler;

import expressions.BadExpressionException;

/**
 * Exception for unrecognized tokens in a line
 * @author MarcoForlini
 */
public class UnrecognizedTokenException extends BadExpressionException {

	private static final long serialVersionUID = 7110673946026942607L;

	/**
	 * Creates a new {@link UnrecognizedTokenException}
	 * @param message	The message
	 */
	public UnrecognizedTokenException (String message) {
		super (message);
	}
	
	/**
	 * Creates a new {@link UnrecognizedTokenException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public UnrecognizedTokenException (String message, Throwable cause) {
		super (message, cause);
	}
	
}
