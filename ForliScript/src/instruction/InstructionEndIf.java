package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.BadExpressionException;

/**
 * Represents the EndIf part of an If
 * @author MarcoForlini
 */
public class InstructionEndIf extends Instruction {
	
	private static final long serialVersionUID = 1479115861973654188L;


	
	/**
	 * Create a new {@link InstructionEndIf}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public InstructionEndIf (String line, int lineNumber, int compiledLineNumber) {
		super(line, lineNumber, compiledLineNumber);
	}


	@Override
	public boolean execute (Program program) throws ExecutionException, BadExpressionException {
		return true;
	}

}
