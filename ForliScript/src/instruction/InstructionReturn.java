package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.BadExpressionException;

/**
 * Represents an instruction which stop the program
 * @author MarcoForlini
 */
public class InstructionReturn extends Instruction {
	
	private static final long serialVersionUID = 8729048043850230029L;

	
	
	/**
	 * Create a new {@link InstructionReturn}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public InstructionReturn (String line, int lineNumber, int compiledLineNumber) {
		super(line, lineNumber, compiledLineNumber);
	}

	@Override
	public boolean execute (Program program) throws ExecutionException, BadExpressionException {
		program.stop();
		return false;
	}
	
}
