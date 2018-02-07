package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.Expression;

/**
 * Represents an instruction which only contains an expression
 * @author MarcoForlini
 */
public class InstructionPrintln extends Instruction {
	
	private static final long serialVersionUID = 4734524242355849175L;
	
	
	
	private Expression expression = null;

	/**
	 * Create a new {@link InstructionPrintln}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param expression			The expression to calculate
	 */
	public InstructionPrintln (String line, int lineNumber, int compiledLineNumber, Expression expression) {
		super(line, lineNumber, compiledLineNumber);
		this.expression = expression;
	}

	@Override
	public boolean execute (Program program) throws ExecutionException {
		System.out.println(expression.eval());
		return true;
	}
	
}
