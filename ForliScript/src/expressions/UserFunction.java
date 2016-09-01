package expressions;


/**
 * A User Defined Function
 * @author MarcoForlini
 */
public class UserFunction implements GenericFunction {
	
	private static final long serialVersionUID = -2963652870559738900L;

	
	
	private final String name;
	private final int numParams;
	private final FunctionCode code;
	
	
	
	/**
	 * Creates a new {@link UserFunction}
	 * @param name			Name of the function. Used to call it.
	 * @param numParams		Number of parameters to pass.
	 * @param code			Code of the function.
	 */
	public UserFunction (String name, int numParams, FunctionCode code) {
		this.name = name;
		this.numParams = numParams;
		this.code = code;
	}




	
	@Override
	public String getName () {
		return name;
	}
	
	@Override
	public String getDescription () {
		return "A user function";
	}
	
	@Override
	public int getMinParams () {
		return numParams;
	}
	
	@Override
	public int getOptParams () {
		return 0;
	}
	
	@Override
	public boolean isConstantResult () {
		return false;
	}
	
	@Override
	public FunctionCode getFunctionCode () {
		return code;
	}
	
}
