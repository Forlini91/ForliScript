package compiler;

/**
 * Exception for errors during the execution of the program
 * @author MarcoForlini
 */
public class ExecutionException extends Exception {
	
	private static final long serialVersionUID = 8570028012115902857L;
	
	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 */
	public ExecutionException (String message) {
		super (message);
	}
	
	/**
	 * Creates a new {@link ExecutionException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public ExecutionException (String message, Throwable cause) {
		super (message, cause);
	}
	
}
