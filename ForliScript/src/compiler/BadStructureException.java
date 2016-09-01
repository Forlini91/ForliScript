package compiler;


/**
 * Exception for bad structure of the program (If and While blocks)
 * @author MarcoForlini
 */
public class BadStructureException extends CompilatorException {

	private static final long serialVersionUID = 2606141338643789804L;
	
	/**
	 * Creates a new {@link BadStructureException}
	 * @param message	The message
	 */
	public BadStructureException (String message) {
		super (message);
	}

	/**
	 * Creates a new {@link BadStructureException}
	 * @param message	The message
	 * @param cause		The cause
	 */
	public BadStructureException (String message, Throwable cause) {
		super (message, cause);
	}

}
