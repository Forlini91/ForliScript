package expressions;

import compiler.ExecutionException;

/**
 * Exception for errors while evaluating an expression
 * @author MarcoForlini
 */
public class EvaluationException extends ExecutionException {
	
	private static final long serialVersionUID = -81363696229135806L;
	
	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 */
	public EvaluationException (String message) {
		super (message);
	}
	
	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public EvaluationException (String message, Throwable cause) {
		super (message, cause);
	}
	
}
