package expressions;

import compiler.ExecutionException;

/**
 * Represents a calculable expression
 * @author MarcoForlini
 */
@FunctionalInterface
public interface Expression extends Token {
	
	/**
	 * Evaluate the expression to a {@link Value} (either a {@link Primitive} or a {@link Variable})
	 * @return The Value
	 * @throws ExecutionException	If an error occur while evaluating the Expression
	 */
	Value eval() throws ExecutionException;

	/**
	 * Evaluate the expression to a {@link Primitive} (if the expression evaluate to a {@link Variable}, return the Constant it is holding)
	 * @return The Constant
	 * @throws ExecutionException	If an error occur while evaluating the Expression
	 */
	default Primitive get() throws ExecutionException {
		return eval().get();
	}

}
