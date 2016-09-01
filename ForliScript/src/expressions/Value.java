package expressions;

import compiler.ExecutionException;

/**
 * Represents a Value
 * @author MarcoForlini
 */
public interface Value extends Expression, Cloneable {

	/** Represents the value <code>null</code> */
	ValNull Null = ValNull.objNull;
	
	/** Represents the boolean value <code>false</code> */
	ValBoolean False = ValBoolean.objFalse;

	/** Represents the boolean value <code>true</code> */
	ValBoolean True = ValBoolean.objTrue;

	/** A value ValString which represents an empty string */
	ValString Empty = ValString.objEmpty;

	

	
	
	/**
	 * Gets this same object. A {@link Value} always evaluate to itself.
	 * @return this
	 */
	@Override
	default Value eval() {
		return this;
	}

	/**
	 * Gets the {@link Primitive} of this value. If this value is not a primitive, then this method will extract the Primitive from it.
	 * @return	A primitive
	 */
	Primitive getPrimitive();

	
	/**
	 * Gets the size of this value by using the method {@link #length()} and encapsulate it into a ValNumber object.
	 * @return The size of this value
	 * @throws EvaluationException	If an error occur while evaluating the value
	 */
	default ValNumber size() throws EvaluationException {
		return ValNumber.getNumber(length());
	}

	
	
	
	
	
	/**
	 * Check if this value is a number
	 * @return {@link #True} if it's a number, {@link #False} otherwise
	 * @throws EvaluationException	If there's an error while reading the value
	 */
	ValBoolean isNumber() throws EvaluationException;
	
	/**
	 * Check if this value is a string
	 * @return {@link #True} if it's a string, {@link #False} otherwise
	 * @throws EvaluationException	If there's an error while reading the value
	 */
	ValBoolean isString() throws EvaluationException;

	/**
	 * Check if this value is an array
	 * @return {@link #True} if it's an array, {@link #False} otherwise
	 * @throws EvaluationException	If there's an error while reading the value
	 */
	ValBoolean isArray() throws EvaluationException;

	/**
	 * Tries to convert this value to ValNumber, but throw an exception if conversion fails
	 * @return A new {@link ValNumber} if conversion succeed
	 * @throws EvaluationException	If conversion fails
	 */
	ValNumber convertToNumber() throws EvaluationException;

	/**
	 * Convert this value to string and return a {@link ValString}<p>
	 * For ValString it's the text, for {@link ValNumber} it's the string, for {@link ValArray} it's the list of elements, for {@link ValNull} it's the string "&lt;null&gt;".
	 * @return the string representation of this value
	 */
	ValString convertToString();
	
	/**
	 * Applies floor to this Value and returns a new string
	 * @return A {@link ValNumber} which contains the nearest lower integer to this {@link Value}
	 * @throws EvaluationException		If an error occur while performing the operation
	 */
	ValNumber floor() throws EvaluationException;

	/**
	 * Applies ceil to the value and return the result
	 * @return A {@link ValNumber} which contains the nearest higher integer to this {@link Value}
	 * @throws EvaluationException		If an error occur while performing the operation
	 */
	ValNumber ceil() throws EvaluationException;

	/**
	 * Applies round to the value and return the result
	 * @return A {@link ValNumber} which contains the nearest integer to this {@link Value}
	 * @throws EvaluationException		If an error occur while performing the operationon
	 */
	ValNumber round() throws EvaluationException;
	
	/**
	 * Applies trim to a string and return a new string
	 * @return A {@link ValString} which contains a new String without leading nor trailing whitespace characters
	 * @throws EvaluationException		If an error occur while performing the calculation
	 */
	ValString trim() throws EvaluationException;
	
	
	
	
	
	/**
	 * Adds the given value to this value and returns the result
	 * @param add	The value to add
	 * @return A new {@link Primitive} which contains the result of (this + add)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive add (Value add) throws ExecutionException;
	
	/**
	 * Subtracts the given value from this value and returns the result
	 * @param subtract	The value to subtract
	 * @return A new {@link Primitive} which contains the result of (this - add)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive subtract (Value subtract) throws ExecutionException;

	/**
	 * Multiplies this value by the given value and returns the result
	 * @param multiplier	The multiplier
	 * @return A new {@link Primitive} which contains the result of (this * add)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive multiply (Value multiplier) throws ExecutionException;
	
	/**
	 * Divides this value by the given value and returns the result
	 * @param divisor	The divisor
	 * @return A new {@link Primitive} which contains the result of (this / add)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive divide (Value divisor) throws ExecutionException;

	/**
	 * Divides this value by the given value and returns the rest
	 * @param divisor	The divisor
	 * @return A new {@link Primitive} which contains the result of (this % add)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive module (Value divisor) throws ExecutionException;
	
	/**
	 * Elevates this value to the given value and returns the result
	 * @param exponent	The exponent
	 * @return A new {@link Primitive} which contains the result of (this ^ exp)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive power (Value exponent) throws ExecutionException;
	
	/**
	 * Calculates the negated of this value and returns the result
	 * @return A new {@link Primitive} which contains the result of (-this)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive negate () throws ExecutionException;
	
	
	/**
	 * Calculates the bitwise and between this and the given value and returns the result
	 * @param other The other value
	 * @return A new {@link Primitive} which contains the result of (this &amp; other)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive bitwiseAnd(Value other) throws ExecutionException;

	/**
	 * Calculates the bitwise or between this and the given value and returns the result
	 * @param other The other value
	 * @return A new {@link Primitive} which contains the result of (this | other)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive bitwiseOr(Value other) throws ExecutionException;
	
	/**
	 * Calculates the bitwise not of this value and returns the result
	 * @return A new {@link Primitive} which contains the result of (~this)
	 * @throws ExecutionException		If an error occur while performing the operation
	 */
	Primitive bitwiseNot() throws ExecutionException;
	
	
	
	
	
	/**
	 * Checks if this value is equal to the passed value and returns the result
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this == other) is <code>true</code>, {@link #False} otherwise
	 */
	ValBoolean equalTo (Value other);
	
	/**
	 * Checks if this value is different than the passed value
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this != other) is <code>true</code>, {@link #False} otherwise
	 */
	ValBoolean differentThan (Value other);
	
	/**
	 * Checks if this value is less than the passed value
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this &lt; other) is <code>true</code>, {@link #False} otherwise
	 * @throws ExecutionException If the objects can't be compared
	 */
	ValBoolean lessThan (Value other) throws ExecutionException;
	
	/**
	 * Checks if this value is less than or equal to the passed value
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this &lt;= other) is <code>true</code>, {@link #False} otherwise
	 * @throws ExecutionException If the objects can't be compared
	 */
	ValBoolean lessEqualThan (Value other) throws ExecutionException;
	
	/**
	 * Checks if this value is greater than the passed value
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this &gt; other) is <code>true</code>, {@link #False} otherwise
	 * @throws ExecutionException If the objects can't be compared
	 */
	ValBoolean greaterThan (Value other) throws ExecutionException;
	
	/**
	 * Checks if this value is greater than or equal to the passed value
	 * @param other	The value to compare
	 * @return {@link #True} it the result of (this &gt;= other) is <code>true</code>, {@link #False} otherwise
	 * @throws ExecutionException If the objects can't be compared
	 */
	ValBoolean greaterEqualThan (Value other) throws ExecutionException;





	/**
	 * Gets the raw float value of this Value. For {@link ValNumber} this is the number, while for other implementations this is the {@link #hashCode()}.
	 * @return The raw float value
	 */
	float value();

	/**
	 * Gets the length of this Value. The lenght may be the number of digits in a number, the number of characters in a string or the number of elements in an array
	 * @return The length of this Value
	 */
	int length();

	/**
	 * Checks if this Value is "true" (not null/0/empty/etc...)
	 * @return true if this Value is "true", false otherwise
	 */
	boolean isTrue();
	
	/**
	 * Builds and returns a clone of this Value (some implementations have few unique instances and may return themselves)
	 * @return	The cloned value
	 */
	Primitive clone ();
	
}
