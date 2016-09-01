package instruction;

import compiler.ExecutionException;
import compiler.Program;
import expressions.BadExpressionException;

/**
 * Represents the instruction IfJump (used internally to handle the termination of If/ElseIf blocks)
 * @author MarcoForlini
 */
public class InstructionJump extends Instruction {
	
	private static final long serialVersionUID = 5439230045406269353L;
	


	private int destination = -1;
	
	/**
	 * Create a new {@link InstructionJump}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public InstructionJump(String line, int lineNumber, int compiledLineNumber) {
		super(line, lineNumber, compiledLineNumber);
	}

	/**
	 * Create a new {@link InstructionJump}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param destination			The destination of the jump
	 */
	public InstructionJump(String line, int lineNumber, int compiledLineNumber, int destination) {
		super(line, lineNumber, compiledLineNumber);
		this.destination = destination;
	}
	
	/**
	 * Set the position where to jump
	 * @param destination	the position where to jump
	 */
	public void setJump (int destination) {
		this.destination = destination;	}

	@Override
	public boolean execute(Program program) throws ExecutionException, BadExpressionException {
		program.jumpTo(destination);
		return false;
	}

}
