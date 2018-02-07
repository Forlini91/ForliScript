package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.Expression;

/**
 * Represents an instruction which only contains an expression
 * @author MarcoForlini
 */
public class InstructionExpression extends Instruction {
	
	private static final long serialVersionUID = 7136311428462303705L;



	private Expression expression = null;

	/**
	 * Create a new {@link InstructionExpression}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param expression			The expression to calculate
	 */
	public InstructionExpression (String line, int lineNumber, int compiledLineNumber, Expression expression) {
		super(line, lineNumber, compiledLineNumber);
		this.expression = expression;
	}

	@Override
	public boolean execute (Program program) throws ExecutionException {
		expression.eval();
		return true;
	}

}
