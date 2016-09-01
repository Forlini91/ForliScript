package expressions;

import compiler.ExecutionException;

/**
 * Represents an array element
 * @author MarcoForlini
 */
public class ArrayElement implements VariableValue, VariableContent, SubValue, Primitive {
	
	private static final long serialVersionUID = 5168335934451461151L;


	
	/**
	 * A functional interface to replace this value in the array
	 * @author MarcoForlini
	 */
	public static interface Setter {
		/**
		 * Replace this value in the array
		 * @param value	A new value
		 * @return		The new value
		 * @throws ExecutionException	If an error occur while gettin the value
		 */
		Primitive set(Value value) throws ExecutionException;
	}
	
	private Primitive value;
	private Setter setter;

	/**
	 * Create a new {@link ArrayElement}
	 * @param value The value
	 * @param setter Function to change the value
	 * @throws EvaluationException	If an error occur while reading the element
	 */
	public ArrayElement (Primitive value, Setter setter) throws EvaluationException{
		this.value = value;
		this.setter = setter;
	}
	
	
	
	
	@Override
	public Primitive eval () {
		return this;
	}

	
	@Override
	public Primitive getPrimitive () {
		return value;
	}
	
	
	@Override
	public Primitive set (Value newValue) throws ExecutionException {
		return setter.set(newValue);
	}
	
	
	@Override
	public Primitive getNotNull () throws EvaluationException {
		if (value == Null){
			throw new EvaluationException ("Array element is null");
		}
		return value;
	}

	
	@Override
	public ValBoolean isSet () {
		return value != Null ? True : False;
	}
	
	
	@Override
	public Value get (int index) throws ExecutionException {
		Primitive element = getNotNull();
		if (element instanceof SubValueRead){
			return ((SubValueRead) element).get(index);
		}
		throw accessSubValueException.apply(element);
	}

	@Override
	public Value set (int index, Value newValue) throws ExecutionException {
		Primitive element = getNotNull();
		if (element instanceof SubValue){
			return ((SubValue) element).set(index, newValue);
		}
		throw accessSubValueException.apply(element);
	}
	
	
	
	
	@Override
	public Primitive addAndSet (Value add) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).addAndSet(add);
		}
		return setter.set(value.add(add));
	}
	
	@Override
	public Primitive subtractAndSet (Value subtract) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).subtractAndSet(subtract);
		}
		return setter.set(value.subtract(subtract));
	}
	
	@Override
	public Primitive multiplyAndSet (Value multiplier) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).multiplyAndSet(multiplier);
		}
		return setter.set(value.multiply(multiplier));
	}
	
	@Override
	public Primitive divideAndSet (Value divisor) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).divideAndSet(divisor);
		}
		return setter.set(value.divide(divisor));
	}
	
	@Override
	public Primitive moduleAndSet (Value divisor) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).moduleAndSet(divisor);
		}
		return setter.set(value.module(divisor));
	}
	
	@Override
	public Primitive powerAndSet (Value exponent) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).powerAndSet(exponent);
		}
		return setter.set(value.power(exponent));
	}
	
	@Override
	public Primitive bitwiseAndAndSet (Value other) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).bitwiseAndAndSet(other);
		}
		return setter.set(value.bitwiseAnd(other));
	}
	
	@Override
	public Primitive bitwiseOrAndSet (Value other) throws ExecutionException {
		if (value instanceof VariableContent){
			return ((VariableContent) value).bitwiseOrAndSet(other);
		}
		return setter.set(value.bitwiseOr(other));
	}
	
	
	

	

	@Override
	public ValBoolean isNumber () throws EvaluationException {
		return value.isNumber();
	}

	@Override
	public ValBoolean isString () throws EvaluationException {
		return value.isString();
	}

	@Override
	public ValBoolean isArray () throws EvaluationException {
		return value.isArray();
	}

	@Override
	public ValNumber convertToNumber () throws EvaluationException {
		return value.convertToNumber();
	}

	@Override
	public ValString convertToString () {
		return value.convertToString();
	}

	@Override
	public ValNumber floor () throws EvaluationException {
		return value.floor();
	}

	@Override
	public ValNumber ceil () throws EvaluationException {
		return value.ceil();
	}

	@Override
	public ValNumber round () throws EvaluationException {
		return value.round();
	}

	@Override
	public ValString trim () throws EvaluationException {
		return value.trim();
	}










	@Override
	public Primitive add (Value add) throws ExecutionException {
		return value.add(add);
	}

	@Override
	public Primitive subtract (Value subtract) throws ExecutionException {
		return value.subtract(subtract);
	}

	@Override
	public Primitive multiply (Value multiplier) throws ExecutionException {
		return value.multiply(multiplier);
	}

	@Override
	public Primitive divide (Value divisor) throws ExecutionException {
		return value.divide(divisor);
	}

	@Override
	public Primitive module (Value divisor) throws ExecutionException {
		return value.module(divisor);
	}

	@Override
	public Primitive power (Value exponent) throws ExecutionException {
		return value.power(exponent);
	}

	@Override
	public Primitive negate () throws ExecutionException {
		return value.negate();
	}
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		return value.bitwiseAnd(other);
	}

	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		return value.bitwiseOr(other);
	}

	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		return value.bitwiseNot();
	}
	
	
	
	

	@Override
	public ValBoolean equalTo (Value other) {
		return value.equalTo(other);
	}

	@Override
	public ValBoolean differentThan (Value other) {
		return value.differentThan(other);
	}

	@Override
	public ValBoolean lessThan (Value other) throws ExecutionException {
		return value.lessThan(other);
	}

	@Override
	public ValBoolean lessEqualThan (Value other) throws ExecutionException {
		return value.lessEqualThan(other);
	}

	@Override
	public ValBoolean greaterThan (Value other) throws ExecutionException {
		return value.greaterThan(other);
	}

	@Override
	public ValBoolean greaterEqualThan (Value other) throws ExecutionException {
		return value.greaterEqualThan(other);
	}





	@Override
	public float value () {
		return value.value();
	}

	@Override
	public int length () {
		return value.length();
	}

	@Override
	public boolean isTrue () {
		return value.isTrue();
	}

	@Override
	public boolean equals (Object other) {
		return value.equals(other);
	}

	@Override
	public Primitive clone () {
		return value.clone().getPrimitive();
	}

	@Override
	public String toString () {
		return value.toString();
	}

}