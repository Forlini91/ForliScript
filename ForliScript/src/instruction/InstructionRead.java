package instruction;

import compiler.CompilatorException;
import compiler.ExecutionException;
import compiler.Program;
import compiler.Utils;
import expressions.BadExpressionException;
import expressions.Expression;
import expressions.ValNumber;
import expressions.ValString;
import expressions.Variable;

/**
 * Represents an instruction which only contains an expression
 * @author MarcoForlini
 */
public class InstructionRead extends Instruction {
	
	private static final long serialVersionUID = 7775323029061673000L;
	


	private Variable variable = null;

	/**
	 * Create a new {@link InstructionRead}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param expression			The expression which must evaluate to a variable
	 * @throws CompilatorException	If the expression is not a variable
	 */
	public InstructionRead (String line, int lineNumber, int compiledLineNumber, Expression expression) throws CompilatorException {
		super(line, lineNumber, compiledLineNumber);
		if (expression instanceof Variable){
			variable = (Variable) expression;
		} else {
			throw new CompilatorException("Instruction Read can only assign the value to a variable");
		}
	}

	@Override
	public boolean execute (Program program) throws ExecutionException, BadExpressionException {
		String input = Utils.scanner.nextLine();
		try {
			float number = Utils.toNumber(input);
			variable.set(ValNumber.getNumber(number));
		} catch (NumberFormatException e){
			variable.set(new ValString(input));
		}
		return true;
	}
	
}
