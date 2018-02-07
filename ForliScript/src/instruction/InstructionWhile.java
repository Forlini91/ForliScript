package instruction;


import compiler.ExecutionException;
import compiler.Program;
import expressions.Expression;


/**
 * Represents the instruction If
 *
 * @author MarcoForlini
 */
public class InstructionWhile extends Instruction {
	
	private static final long serialVersionUID = 5823514108635950566L;

	
	
	private Expression condition = null;
	private int	falseDestination = -1;
	
	/**
	 * Create a new {@link InstructionWhile}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 * @param condition				The condition
	 */
	public InstructionWhile (String line, int lineNumber, int compiledLineNumber, Expression condition) {
		super(line, lineNumber, compiledLineNumber);
		this.condition = condition;
	}
	
	/**
	 * Sets the position where to jump if condition is false
	 *
	 * @param falseDestination	the destination of the jump if condition is false
	 */
	public void setFalseJump (int falseDestination) {
		this.falseDestination = falseDestination;
	}
	
	@Override
	public boolean execute (Program program) throws ExecutionException {
		if (condition.eval().isTrue()) {
			return true;
		}
		program.jumpTo(falseDestination);
		return false;
	}
	
}
