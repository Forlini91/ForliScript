package expressions;

import compiler.ExecutionException;

/**
 * Represents a Container which can be assigned a value
 * @author MarcoForlini
 */
public interface VariableValue extends Value {
	
	/**
	 * Assign a new value to this Assignable
	 * @param newValue	The new value
	 * @return			The new value
	 * @throws ExecutionException	If an error occur while assigning the value
	 */
	public Value set (Value newValue) throws ExecutionException;

	/**
	 * Get the value from the variable if not null
	 * @return	The value
	 * @throws EvaluationException	If the value is null
	 */
	public Value getNotNull() throws EvaluationException;
	
	/**
	 * Check if the variable is not null
	 * @return	TRUE if not null, false otherwise
	 */
	public ValBoolean isSet();

}
