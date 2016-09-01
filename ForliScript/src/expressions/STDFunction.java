package expressions;

import java.util.ArrayList;
import java.util.List;

import compiler.Utils;

/**
 * Represents a standard function
 * @author MarcoForlini
 */
public enum STDFunction implements GenericFunction {
	
	/** Create a shallow copy of the given input */
	COPY ("Copy", "Create a shallow copy of the given value (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].clone();
			}),
	
	
	
	/** Return a random number */
	RANDOM ("Random", "Generate a random number between 0 and max [Number::max = 1]", 0, 1,
			false, (Value...params) -> {
				if (params.length > 0){
					return ValNumber.getNumber(Utils.random.nextInt((int) params[0].value()));
				}
				return ValNumber.getNumber(Utils.random.nextFloat());
			}),
	
	/** Get the length of the value */
	SIZE ("Size", "Get the size of the value (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].size();
			}),
	
	/** Apply floor to a number */
	FLOOR ("Floor", "Apply Floor to a number (Number::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].floor();
			}),

	/** Apply floor to a number */
	CEIL ("Ceil", "Apply Ceil to a number (Number::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].ceil();
			}),

	/** Round the given number to the nearest integer */
	ROUND ("Round", "Apply Round to a number (Number::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].round();
			}),
	
	/** Apply trim to a string */
	TRIM ("Trim", "Get a string without any leading and trailing whitespace (String::text)", 1, 0,
			true, (Value...params) -> {
				return params[0].trim();
			}),
	
	

	/** Check if the given input is a number */
	IS_NUMBER ("IsNumber", "Check if the value is a number or can be converted to Number (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].isNumber();
			}),
	
	/** Check if the given input is a string */
	IS_STRING ("IsString", "Check if the value is a string (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].isString();
			}),
	
	/** Check if the given input is an array */
	IS_ARRAY ("IsArray", "Check if the value is an array (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].isArray();
			}),
	
	/** Convert the input to number */
	TO_NUMBER ("ToNumber", "Convert the value to a Number, throwing an error if conversion fails (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].convertToNumber();
			}),
	
	/** Convert the input to string */
	TO_STRING ("ToString", "Convert the value to String (Any::value)", 1, 0,
			true, (Value...params) -> {
				return params[0].convertToString();
			}),
	
	/** Create and return a new Array */
	ARRAY ("Array", "Create a new Array which contains the given elements (Any[...]::elements)", 0, -1,
			true, (Value...params) -> {
				boolean constant = true;
				List<Primitive> elements = new ArrayList<>(params.length);
				for (Value value : params){
					if (value instanceof Constant){
						elements.add((Constant) value);
					} else {
						constant = false;
						elements.add(value.getPrimitive());
					}
				}
				if (constant){
					return new ValArrayConst(elements);
				}
				return new ValArray(elements);
			}),
	
	/** Create and return a new Array which contains all elements from the given Arrays */
	MERGE ("Merge", "Merge the given Arrays and create a new array", 2, -1,
			true, (Value...params) -> {
				List<Primitive> union = new ArrayList<>();
				boolean constant = true;
				for (Value value : params){
					if (value instanceof ValArray){
						if (value instanceof ValArrayConst == false){
							constant = false;
						}
						union.addAll(((ValArray) value).getElements());
					} else {
						throw new EvaluationException ("Parameter " + value + " + is not an array");
					}
				}
				if (constant){
					return new ValArrayConst(union);
				}
				return new ValArray(union);
				
			}),

	
	
	
	
	;
	/** Name */				private final String name;
	/** Description */		private final String description;
	/** Min params */		private final int minParams;
	/** Optional params */	private final int optParams;
	/** Constant result */	private final boolean constantResult;
	/** Operator code */	private final FunctionCode code;


	/**
	 * Create a new {@link STDFunction}
	 * @param name				Name of the function
	 * @param description		Description
	 * @param minParams			Min number of parameters. Can't compile with less parameters.
	 * @param optParams			Max number of optional parameters. Can't compile with more than min+optional parameters. If -1, can have unlimited parameters.
	 * @param constantResult	If true, the function result is constant if the parameters are constant OR is constant anyway.
	 * @param code				The function code, which takes from min to min+optional parameters and return a result.
	 */
	private STDFunction (String name, String description, int minParams, int optParams, boolean constantResult, FunctionCode code){
		this.name = name;
		this.description = description;
		this.minParams = minParams;
		this.optParams = optParams;
		this.constantResult = constantResult;
		this.code = code;
	}
	

	/**
	 * Attempt to find a Command with the given name
	 * @param text	The name
	 * @param from	The min position
	 * @param to	The max position
	 * @return	the Command, or null if no Command is found
	 */
	public static STDFunction parse(String text, int from, int to){
		switch(text.substring(from, to)){
			case "Copy": return COPY;
			case "Size": return SIZE;
			case "Random": return RANDOM;
			case "Floor": return FLOOR;
			case "Ceil": return CEIL;
			case "Round": return ROUND;
			case "Trim": return TRIM;
			case "IsNumber": return IS_NUMBER;
			case "IsString": return IS_STRING;
			case "IsArray": return IS_ARRAY;
			case "ToString": return TO_STRING;
			case "ToNumber": return TO_NUMBER;
			case "Array": return ARRAY;
		}
		return null;
	}
	

	@Override
	public String toString () {
		return name;
	}
	
	
	/**
	 * @return the name
	 */
	@Override
	public String getName () {
		return name;
	}
	
	
	/**
	 * @return the description
	 */
	@Override
	public String getDescription () {
		return description;
	}
	
	
	/**
	 * @return the minParams
	 */
	@Override
	public int getMinParams () {
		return minParams;
	}
	
	
	/**
	 * @return the optParams
	 */
	@Override
	public int getOptParams () {
		return optParams;
	}
	
	
	/**
	 * @return the constantResult
	 */
	@Override
	public boolean isConstantResult () {
		return constantResult;
	}
	
	
	/**
	 * @return the code
	 */
	@Override
	public FunctionCode getFunctionCode () {
		return code;
	}
	
}
