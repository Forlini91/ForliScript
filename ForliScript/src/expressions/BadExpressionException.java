package expressions;

import compiler.CompilatorException;
import compiler.ExecutionException;

/**
 * Exception for bugs in the expressions
 * @author MarcoForlini
 */
public class BadExpressionException extends CompilatorException {

	private static final long serialVersionUID = 4297004645305942826L;
	
	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 */
	public BadExpressionException (String message) {
		super (message);
	}

	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public BadExpressionException (String message, Throwable cause) {
		super (message, cause);
	}

}
