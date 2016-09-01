package expressions;

import java.io.Serializable;

import compiler.ExecutionException;

/**
 * Represents an operation that accepts a list of expressions and produce a Value.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #execute(Value...)}.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface FunctionCode extends Serializable {

	/**
	 * Execute the function with the given parameters
	 *
	 * @param params the parameters
	 * @return the return of the command
	 * @throws ExecutionException		If an error occur while evaluating the command or one of the parameters
	 */
	Value execute(Value[] params) throws ExecutionException;

}
