package expressions;

import java.io.Serializable;

import compiler.ExecutionException;

/**
 * Represents an operation that accepts an expression from the right and produce a Value.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #eval(Value)}.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface UnaryOperation extends Serializable {
	
	/**
	 * Applies this operation to the given value.
	 * @param rValue	The value
	 * @return the result Value
	 * @throws ExecutionException		If an error occur when executing the operation
	 */
	Value eval(Value rValue) throws ExecutionException;
	
}