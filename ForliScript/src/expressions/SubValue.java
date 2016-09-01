package expressions;

import compiler.ExecutionException;

/**
 * Represents an object which contains sub values which can be read and write
 * @author MarcoForlini
 */
public interface SubValue extends SubValueRead {

	/**
	 * Set the given Value at index i contained in this Value
	 * @param index		The index
	 * @param newValue	The new value
	 * @return The value assigned to the given index
	 * @throws ExecutionException	If an error occur while reading or writing the value
	 */
	Value set(int index, Value newValue) throws ExecutionException;

}
