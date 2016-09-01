package expressions;

/**
 * Represents a value
 * @author MarcoForlini
 */
public class ValBoolean extends ValNumber {

	private static final long serialVersionUID = -8685627135098510590L;
	
	
	
	/** The value FALSE */
	public static final ValBoolean objFalse = new ValBoolean (0);
	
	/** The value TRUE */
	public static final ValBoolean objTrue = new ValBoolean (1);


	
	/**
	 * Get the boolean object basing on the given value
	 * @param value	The value
	 * @return		The boolean object
	 */
	public static ValBoolean getBool(boolean value){
		return value ? True : False;
	}
	
	/**
	 * Get the boolean object basing on the given value
	 * @param value	The value
	 * @return		The boolean object
	 */
	public static ValBoolean getBool(float value){
		return value != 0 ? True : False;
	}


	/**
	 * Can't create more instances
	 * @param value The boolean value
	 */
	private ValBoolean(int value){
		super(value);
	}
	
	
	

	
	@Override
	public ValBoolean floor () {
		return this;
	}


	@Override
	public ValBoolean ceil () {
		return this;
	}
	
	
	@Override
	public ValBoolean round () {
		return this;
	}

}
