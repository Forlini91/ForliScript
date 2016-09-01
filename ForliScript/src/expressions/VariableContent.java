package expressions;

import compiler.ExecutionException;

/**
 * Represents a variable value
 * @author MarcoForlini
 */
public interface VariableContent extends Value {
	
	/**
	 * Adds the given value to this value, assigns the result to this VariableContent and return the result.
	 * @param add			The value to add
	 * @return A new {@link Primitive} which contains the result of (this + add)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive addAndSet (Value add) throws ExecutionException;

	/**
	 * Subtracts the given value from this value and assigns the result to this VariableContent and return the result.
	 * @param subtract		The value to subtract
	 * @return A new {@link Primitive} which contains the result of (this - add)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive subtractAndSet (Value subtract) throws ExecutionException;
	
	/**
	 * Multiplies this value by the given value and assigns the result to this VariableContent and return the result.
	 * @param multiplier	The multiplier
	 * @return A new {@link Primitive} which contains the result of (this * add)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive multiplyAndSet (Value multiplier) throws ExecutionException;

	/**
	 * Divides this value by the given value and assigns the result to this VariableContent and return the result.
	 * @param divisor		The divisor
	 * @return A new {@link Primitive} which contains the result of (this / add)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive divideAndSet (Value divisor) throws ExecutionException;
	
	/**
	 * Divides this value by the given value and assigns the rest to this VariableContent and return the rest.
	 * @param divisor		The divisor
	 * @return A new {@link Primitive} which contains the result of (this % add)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive moduleAndSet (Value divisor) throws ExecutionException;

	/**
	 * Elevates this value to the given value and assigns the result to this VariableContent and return the result.
	 * @param exponent		The exponent
	 * @return A new {@link Primitive} which contains the result of (this ^ exp)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive powerAndSet (Value exponent) throws ExecutionException;

	/**
	 * Calculates the bitwise and between this and the given value and assigns the result to this VariableContent and return the result.
	 * @param other			The other value
	 * @return A new {@link Primitive} which contains the result of (this &amp; other)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive bitwiseAndAndSet(Value other) throws ExecutionException;
	
	/**
	 * Calculates the bitwise or between this and the given value and assigns the result to this VariableContent and return the result.
	 * @param other			The other value
	 * @return A new {@link Primitive} which contains the result of (this | other)
	 * @throws ExecutionException		If an error occur while performing the operation or while assigning the result
	 */
	Primitive bitwiseOrAndSet(Value other) throws ExecutionException;
	
}
