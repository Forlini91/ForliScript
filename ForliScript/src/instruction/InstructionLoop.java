package instruction;

import compiler.ExecutionException;
import compiler.Program;

/**
 * Instruction which jump back to its While instruction
 * @author MarcoForlini
 */
public class InstructionLoop extends Instruction {

	private static final long serialVersionUID = -667742562319626310L;
	


	private int jumpBack = -1;
	
	
	/**
	 * Create a new {@link InstructionLoop}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public InstructionLoop (String line, int lineNumber, int compiledLineNumber) {
		super(line, lineNumber, compiledLineNumber);
	}

	/**
	 * Set the position where to jump if condition is false
	 * @param jumpBack	the position where to jump
	 */
	public void setJumpBack (int jumpBack) {
		this.jumpBack = jumpBack;
	}
	
	@Override
	public boolean execute(Program program) throws ExecutionException {
		program.jumpTo(jumpBack);
		return false;
	}
	
}