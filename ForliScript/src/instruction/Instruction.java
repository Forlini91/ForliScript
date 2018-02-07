package instruction;

import java.io.Serializable;

import compiler.ExecutionException;
import compiler.Program;

/**
 * Represents an executable instruction
 * @author MarcoForlini
 */
public abstract class Instruction implements Serializable {
	
	private static final long serialVersionUID = 1922094350157919201L;

	
	
	private String line;
	private final int lineNumber;
	private final int compiledLineNumber;
	
	/**
	 * Create a new {@link Instruction}
	 * @param line					The line
	 * @param lineNumber			The line number
	 * @param compiledLineNumber	The compiled line number
	 */
	public Instruction(String line, int lineNumber, int compiledLineNumber){
		this.line = line;
		this.lineNumber = lineNumber+1;
		this.compiledLineNumber = compiledLineNumber;
	}
	
	
	/**
	 * Execute this instruction
	 * @param program				The program running this instruction
	 * @return						true if the programCounter can advance, false otherwise (if the instruction altered it)
	 * @throws ExecutionException	If an error occur during the execution of an instruction
	 */
	public abstract boolean execute(Program program) throws ExecutionException;


	/**
	 * Get the line number of this instruction
	 * @return	The line number
	 */
	public int getLineNumber(){
		return lineNumber;
	}


	/**
	 * Get the compiled line number of this instruction
	 * @return	The compiled line number
	 */
	public int getCompiledLineNumber(){
		return compiledLineNumber;
	}


	@Override
	public final String toString () {
		return lineNumber + " : " + line;
	}

}
