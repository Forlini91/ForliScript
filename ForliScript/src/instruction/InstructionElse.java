package instruction;

import compiler.ExecutionException;
import compiler.Program;

/**
 * Represents the Else part of an If
 * @author MarcoForlini
 */
public class InstructionElse extends Instruction {

	private static final long serialVersionUID = 5018513486578424491L;



	/**
	 * Create a new {@link InstructionElse}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public InstructionElse (String line, int lineNumber, int compiledLineNumber) {
		super(line, lineNumber, compiledLineNumber);
	}
	
	
	@Override
	public boolean execute (Program program) throws ExecutionException {
		return true;
	}

}
