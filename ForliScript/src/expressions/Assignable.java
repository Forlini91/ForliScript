package expressions;

import compiler.ExecutionException;

/**
 * Represents a Container which can be assigned a value
 * @author MarcoForlini
 */
public interface Assignable extends Value {
	
	/**
	 * Assign a new value to this variable
	 * @param newValue	The new value
	 * @return			The new value
	 * @throws ExecutionException	If an error occur while assigning the value
	 */
	public Value set (Value newValue) throws ExecutionException;
	
	/**
	 * Check if the variable is not null
	 * @return	TRUE if not null, false otherwise
	 */
	public ValBoolean isSet();
	
}
