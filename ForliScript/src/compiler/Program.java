package compiler;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import expressions.ValNumber;
import expressions.ValString;
import expressions.Value;
import expressions.Variable;
import instruction.Instruction;

/**
 * Represents a program ready to be runned
 * @author MarcoForlini
 */
public class Program implements Serializable {
	
	private static final long serialVersionUID = 2000874369234491493L;
	
	/** The list of instructions */
	private final List<Instruction> instructions;
	private final Map<String, Variable> variables;
	private final int n;

	/** The current instruction */
	private int programCounter = 0;

	/**
	 * Create a new {@link Program}
	 * @param instructions	The list of instructions
	 * @param variables		The map of variables
	 */
	Program(List<Instruction> instructions, Map<String, Variable> variables){
		this.instructions = instructions;
		this.variables = variables;
		n = instructions.size();
	}
	
	/**
	 * Execute the list of instructions of the program
	 * @throws ExecutionException	If the program contains an error
	 */
	public void run() throws ExecutionException {
		programCounter = 0;
		Instruction instruction = null;
		while (programCounter < n){
			instruction = instructions.get(programCounter);
			try{
				if (instruction.execute(this)){
					programCounter++;
				}
			} catch (Exception e){
				throw new ExecutionException("Error while executing instruction:\n\t\t" + instruction, e);
			}
		}
	}
	
	/**
	 * Jump to the given position
	 * @param position	the new position
	 */
	public void jumpTo(int position){
		programCounter = position;
	}

	/**
	 * Stop the program
	 */
	public void stop () {
		programCounter = n;	//Jump to the end of the program
	}


	/**
	 * Assign the given value to the given variable. If no variable exists with the given name, it will be created.
	 * @param name	The name
	 * @param value	The new value
	 * @throws ExecutionException	If the variable is constant
	 */
	public void setVariable(String name, float value) throws ExecutionException{
		if (name.isEmpty()){
			throw new ExecutionException("Variable must have a name");
		} else if (name.equals("null")){
			throw new ExecutionException("Variable can't have name \"null\"");
		}
		char c = name.charAt(0);
		if (!Character.isLetter(c) || Character.isUpperCase(c)) {
			throw new ExecutionException("Variable name must start with a lower case letter");
		}
		Variable variable = variables.get(name);
		if (variable != null){
			variable.set(ValNumber.getNumber(value));
		} else {
			variable = new Variable(name, ValNumber.getNumber(value), false);
			variables.put(name, variable);
		}
	}
	
	/**
	 * Assign the given text to the given variable. If no variable exists with the given name, it will be created.
	 * @param name	The name
	 * @param text	The new text
	 * @throws ExecutionException	If the variable is constant
	 */
	public void setVariable(String name, String text) throws ExecutionException{
		if (name.isEmpty()){
			throw new ExecutionException("Variable must have a name");
		} else if (name.equals("null")){
			throw new ExecutionException("Variable can't have name \"null\"");
		}
		char c = name.charAt(0);
		if (!Character.isLetter(c) || Character.isUpperCase(c)) {
			throw new ExecutionException("Variable name must start with a lower case letter");
		}
		Variable variable = variables.get(name);
		if (variable != null){
			variable.set(new ValString(text));
		} else {
			variable = new Variable(name, new ValString(text), false);
			variables.put(name, variable);
		}
	}
	
	/**
	 * Assign null to the given variable. If no variable exists with the given name, it will be created.
	 * @param name	The name
	 * @throws ExecutionException	If the variable is constant
	 */
	public void setVariable(String name) throws ExecutionException{
		if (name.isEmpty()){
			throw new ExecutionException("Variable must have a name");
		} else if (name.equals("null")){
			throw new ExecutionException("Variable can't have name \"null\"");
		}
		char c = name.charAt(0);
		if (!Character.isLetter(c) || Character.isUpperCase(c)) {
			throw new ExecutionException("Variable name must start with a lower case letter");
		}
		Variable variable = variables.get(name);
		if (variable != null){
			variable.set(Value.Null);
		} else {
			variables.put(name, new Variable(name, Value.Null, false));
		}
	}
	
}
