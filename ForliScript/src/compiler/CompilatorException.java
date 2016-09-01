package compiler;


/**
 * An exception happened when the program is being compiled
 * @author MarcoForlini
 */
public class CompilatorException extends Exception {
	
	private static final long serialVersionUID = -1534263338235723177L;
	
	/**
	 * Creates a new {@link CompilatorException}
	 * @param message	The message
	 */
	public CompilatorException (String message) {
		super (message);
	}
	
	/**
	 * Creates a new {@link CompilatorException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public CompilatorException (String message, Throwable cause) {
		super (message, cause);
	}
	
}
