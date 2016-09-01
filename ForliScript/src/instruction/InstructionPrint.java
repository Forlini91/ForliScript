package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.BadExpressionException;
import expressions.Expression;

/**
 * Represents an instruction which only contains an expression
 * @author MarcoForlini
 */
public class InstructionPrint extends Instruction {

	private static final long serialVersionUID = 5436689889168893484L;
	


	private Expression expression = null;
	
	/**
	 * Create a new {@link InstructionPrint}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param expression			The expression to calculate
	 */
	public InstructionPrint (String line, int lineNumber, int compiledLineNumber, Expression expression) {
		super(line, lineNumber, compiledLineNumber);
		this.expression = expression;
	}
	
	@Override
	public boolean execute (Program program) throws ExecutionException, BadExpressionException {
		System.out.print(expression.eval());
		return true;
	}

}
