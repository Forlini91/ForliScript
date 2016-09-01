package expressions;

import compiler.ExecutionException;

/**
 * Represents a variable which can hold a value
 * @author MarcoForlini
 */
public class Pointer implements VariableValue, VariableContent, SubValue  {
	
	private static final long serialVersionUID = -2513317002705315790L;
	
	private static final ExecutionException constantVariable = new ExecutionException ("Can't assign a new value to a constant variable");

	

	/** Name of the variable */
	public final String name;

	/** Value of variable */
	private Value value;

	/** If true, the variable is constant */
	private final boolean constant;



	/**
	 * Create a new {@link Pointer}
	 * @param name		Name
	 * @param constant	If true, the pointer is constant
	 */
	public Pointer (String name, boolean constant) {
		this(name, Null, constant);
	}
	
	
	/**
	 * Create a new {@link Variable}
	 * @param name		Name
	 * @param value		The first value
	 * @param constant	If true, the variable is constant
	 */
	public Pointer (String name, Value value, boolean constant){
		this.name = name;
		this.value = value;
		this.constant = constant;
	}
	
	
	


	/**
	 * Get the value from the variable
	 * @return 	The value contained in this variable
	 */
	@Override
	public Primitive getPrimitive() {
		return value.getPrimitive();
	}
	
	
	
	/**
	 * Assign a new value to this variable
	 * @param newValue	The new value
	 * @return			The new value
	 * @throws ExecutionException	If an error occur while assigning the value
	 */
	@Override
	public Value set (Value newValue) throws ExecutionException {
		if (constant && value != Null){
			throw constantVariable;
		}
		return value = newValue;
	}
	
	
	@Override
	public Value getNotNull() {
		if (value == Null){
			throw new NullPointerException ("Variable " + name + " is null");
		}
		return value;
	}

	
	/**
	 * Check if the variable is not null
	 * @return	TRUE if not null, false otherwise
	 */
	@Override
	public ValBoolean isSet(){
		return value != Null ? True : False;
	}
	
	
	/**
	 * @return The result of value.get(i)
	 */
	@Override
	public Value get (int i) throws ExecutionException {
		Value v = getNotNull();
		if (v instanceof SubValueRead) {
			return ((SubValueRead) v).get(i);
		}
		throw accessSubValueException.apply(v);
	}


	/**
	 * @return The result of value.set(i, newValue)
	 */
	@Override
	public Value set (int i, Value newValue) throws ExecutionException {
		if (constant){
			throw constantVariable;
		}
		Value v = getNotNull();
		if (v instanceof SubValue){
			return ((SubValue) v).set(i, newValue);
		}
		throw accessSubValueException.apply(v);
	}





	@Override
	public Primitive addAndSet (Value add) throws ExecutionException {
		return set(add(add)).getPrimitive();
	}
	
	@Override
	public Primitive subtractAndSet (Value subtract) throws ExecutionException {
		return set(subtract(subtract)).getPrimitive();
	}

	@Override
	public Primitive multiplyAndSet (Value multiplier) throws ExecutionException {
		return set(multiply(multiplier)).getPrimitive();
	}
	
	@Override
	public Primitive divideAndSet (Value divisor) throws ExecutionException {
		return set(divide(divisor)).getPrimitive();
	}

	@Override
	public Primitive moduleAndSet (Value divisor) throws ExecutionException {
		return set(module(divisor)).getPrimitive();
	}
	
	@Override
	public Primitive powerAndSet (Value exponent) throws ExecutionException {
		return set(power(exponent)).getPrimitive();
	}
	
	@Override
	public Primitive bitwiseAndAndSet(Value other) throws ExecutionException {
		return set(bitwiseAnd(other)).getPrimitive();
	}

	@Override
	public Primitive bitwiseOrAndSet(Value other) throws ExecutionException {
		return set(bitwiseOr(other)).getPrimitive();
	}
	


	
	
	/**
	 * @return The result of value.add(add)
	 */
	@Override
	public Primitive add (Value add) throws ExecutionException {
		return getNotNull().add(add.getPrimitive());
	}
	
	
	/**
	 * @return The result of value.multiply(subtract)
	 */
	@Override
	public Primitive subtract (Value subtract) throws ExecutionException {
		return getNotNull().subtract(subtract);
	}
	
	
	/**
	 * @return The result of value.multiply(multiplier)
	 */
	@Override
	public Primitive multiply (Value multiplier) throws ExecutionException {
		return getNotNull().multiply(multiplier);
	}
	
	
	/**
	 * @return The result of value.divide(divisor)
	 */
	@Override
	public Primitive divide (Value divisor) throws ExecutionException {
		return getNotNull().divide(divisor);
	}


	/**
	 * @return The result of value.modult(divisor)
	 */
	@Override
	public Primitive module (Value divisor) throws ExecutionException {
		return getNotNull().module(divisor);
	}


	/**
	 * @return The result of value.power(exponent)
	 */
	@Override
	public Primitive power (Value exponent) throws ExecutionException {
		return getNotNull().power(exponent);
	}


	/**
	 * @return The result of value.negate()
	 */
	@Override
	public Primitive negate () throws ExecutionException {
		return getNotNull().negate();
	}
	
	
	/**
	 * @return The result of value.bitwiseAnd(other)
	 */
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		return getNotNull().bitwiseAnd(other);
	}
	
	
	/**
	 * @return The result of value.bitwiseOr(other)
	 */
	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		return getNotNull().bitwiseOr(other);
	}
	
	
	/**
	 * @return The result of value.bitwiseNot()
	 */
	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		return getNotNull().bitwiseNot();
	}
	
	
	
	
	
	/**
	 * @return The result of value.equalTo(other)
	 */
	@Override
	public ValBoolean equalTo (Value other) {
		return value.equalTo(other);
	}
	
	/**
	 * @return The result of value.differentThan(other)
	 */
	@Override
	public ValBoolean differentThan (Value other) {
		return value.differentThan(other);
	}
	
	/**
	 * @return The result of value.lessThan(other)
	 */
	@Override
	public ValBoolean lessThan (Value other) throws ExecutionException {
		return getNotNull().lessThan(other);
	}
	
	/**
	 * @return The result of value.lessEqualThan(other)
	 */
	@Override
	public ValBoolean lessEqualThan (Value other) throws ExecutionException {
		return getNotNull().lessEqualThan(other);
	}
	
	/**
	 * @return The result of value.greaterThan(other)
	 */
	@Override
	public ValBoolean greaterThan (Value other) throws ExecutionException {
		return getNotNull().greaterThan(other);
	}
	
	/**
	 * @return The result of value.greaterEqualThan(other)
	 */
	@Override
	public ValBoolean greaterEqualThan (Value other) throws ExecutionException {
		return getNotNull().greaterEqualThan(other);
	}




	/**
	 * @return The result of value.floor()
	 */
	@Override
	public ValNumber floor () throws EvaluationException {
		return getNotNull().floor();
	}

	/**
	 * @return The result of value.ceil()
	 */
	@Override
	public ValNumber ceil () throws EvaluationException {
		return getNotNull().ceil();
	}

	/**
	 * @return The result of value.round()
	 */
	@Override
	public ValNumber round () throws EvaluationException {
		return getNotNull().round();
	}

	/**
	 * @return The result of value.trim()
	 */
	@Override
	public ValString trim () throws EvaluationException {
		return getNotNull().trim();
	}

	
	
	
	
	/**
	 * @return The result of value.isNumber()
	 */
	@Override
	public ValBoolean isNumber () throws EvaluationException {
		return getNotNull().isNumber();
	}

	/**
	 * @return The result of value.isString()
	 */
	@Override
	public ValBoolean isString () throws EvaluationException {
		return getNotNull().isString();
	}

	/**
	 * @return The result of value.isArray()
	 */
	@Override
	public ValBoolean isArray () throws EvaluationException {
		return getNotNull().isArray();
	}

	/**
	 * @return The result of value.convertToNumber()
	 */
	@Override
	public ValNumber convertToNumber () throws EvaluationException {
		return getNotNull().convertToNumber();
	}

	/**
	 * @return The result of value.convertToString()
	 */
	@Override
	public ValString convertToString () {
		return value.convertToString();
	}





	/**
	 * @return The result of value.getValue()
	 */
	@Override
	public float value () {
		return getNotNull().value();
	}


	/**
	 * @return The result of value.length()
	 */
	@Override
	public int length () {
		return getNotNull().length();
	}
	
	
	/**
	 * @return The result of value.isTrue()
	 */
	@Override
	public boolean isTrue () {
		return value.isTrue();
	}
	

	@Override
	public boolean equals (Object other) {
		return value.equals(other);
	}
	

	@Override
	public Primitive clone ()  {
		return value.clone();
	}

	
	@Override
	public String toString () {
		return value.toString();
	}

}
