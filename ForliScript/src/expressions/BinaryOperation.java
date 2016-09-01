package expressions;

import java.io.Serializable;

import compiler.ExecutionException;

/**
 * Represents an operation that accepts a left expression and a right expression and produce a Value.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #eval(Value, Value)}.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface BinaryOperation extends Serializable {

	/**
	 * Applies this operation to the given expressions.
	 *
	 * @param lValue the left Value
	 * @param rValue the right Value
	 * @return the result Value
	 * @throws ExecutionException		If an error occur while executing the operation
	 */
	Value eval(Value lValue, Value rValue) throws ExecutionException;

}
