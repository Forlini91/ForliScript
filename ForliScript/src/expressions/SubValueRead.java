package expressions;

import java.util.function.Function;

import compiler.ExecutionException;

/**
 * Represents an object which contains sub values which can only be read
 * @author MarcoForlini
 */
public interface SubValueRead {

	/**
	 * Function which return a "Can't access" exception
	 */
	Function <Value, ExecutionException> accessSubValueException = (Value value) -> new ExecutionException ("Can't access a sub element in " + value);

	/**
	 * Get the Value at index i contained in this Value
	 * @param index The index
	 * @return The Value at the given index
	 * @throws ExecutionException	If an error occur while accessing the value
	 */
	Value get(int index) throws ExecutionException;

}
