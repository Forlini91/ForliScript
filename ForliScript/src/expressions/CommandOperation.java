package expressions;

import java.io.Serializable;

import compiler.ExecutionException;

/**
 * Represents an operation that accepts a left expression and a right expression and produce a Value.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #execute(Value...)}.
 *
 * @since 1.8
 */
@FunctionalInterface
public interface CommandOperation extends Serializable {

	/**
	 * Applies the parameters to this command.
	 *
	 * @param params the parameters
	 * @return the return of the command
	 * @throws ExecutionException		If an error occur while evaluating the command or one of the parameters
	 */
	Value execute(Value...params) throws ExecutionException;

}
