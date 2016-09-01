package expressions;

import compiler.ExecutionException;
import compiler.Utils;

/**
 * Represents the primitive value Number
 * @author MarcoForlini
 */
public class ValNumber implements Constant, SubValueRead {
	
	private static final long serialVersionUID = -4455785864084100169L;
	
	
	private static final ValNumber ZERO = new ValNumber(0);
	private static final ValNumber ONE = new ValNumber(1);
	


	/** The raw numeric value */
	private float value;




	private ValNumber getNumberInternal(float value){
		if (this.value == value){
			return this;
		}
		return getNumber(value);
	}
	
	
	/**
	 * Gets a {@link ValNumber} object or use an existing one
	 * @param value		The value
	 * @return			A {@link ValNumber} object
	 */
	public static ValNumber getNumber(float value){
		if (value == 0) {
			return ZERO;
		} else if (value == 1) {
			return ONE;
		}
		return new ValNumber(value);
	}



	/**
	 * Create a new {@link ValNumber}
	 * @param value The initial value
	 */
	ValNumber (float value){
		this.value = value;
	}
	
	

	
	
	@Override
	public ValNumber eval () {
		return this;
	}

	@Override
	public ValNumber getPrimitive () {
		return this;
	}
	
	@Override
	public Primitive get (int i) throws EvaluationException {
		try {
			return getNumberInternal(toString().charAt(i));
		} catch (IndexOutOfBoundsException e){
			throw new EvaluationException ("Can't extract digit ad index " + i, e);
		}
	}
	
	



	@Override
	public ValBoolean isNumber () {
		return True;
	}
	
	
	@Override
	public ValBoolean isString () {
		return False;
	}
	
	
	@Override
	public ValBoolean isArray () {
		return False;
	}

	
	@Override
	public ValNumber convertToNumber () {
		return this;
	}

	
	@Override
	public ValString convertToString () {
		return new ValString(Utils.valueToString(value));
	}

	
	@Override
	public ValNumber floor () {
		return getNumberInternal((float) Math.floor(value));
	}
	
	
	@Override
	public ValNumber ceil () {
		return getNumberInternal((float) Math.ceil(value));
	}


	@Override
	public ValNumber round () {
		return getNumberInternal(Math.round(value));
	}
	
	
	@Override
	public ValString trim () throws EvaluationException {
		throw new EvaluationException("Can't apply trim to a number");
	}
	




	@Override
	public Primitive add (Value toAdd) throws ExecutionException {
		if (toAdd instanceof ValString) {
			return new ValString (Utils.valueToString(value) + toAdd);
		} else if (toAdd instanceof ValArray){
			return ((ValArray) toAdd).add(this);
		}
		return getNumberInternal(value + toAdd.value());
	}
	

	@Override
	public Primitive subtract (Value subtract) throws ExecutionException {
		if (subtract instanceof ValNumber == false){
			throw new ExecutionException ("Can only subtract a number from a number");
		}
		return getNumberInternal(value - subtract.value());
	}
	

	@Override
	public Primitive multiply (Value multiplier) throws ExecutionException {
		if (multiplier instanceof ValString){
			return ((ValString) multiplier).multiply(this);
		} else if (multiplier instanceof ValArray){
			return ((ValArray) multiplier).multiply(this);
		}
		return getNumberInternal(value * multiplier.value());
	}
	

	@Override
	public Primitive divide (Value divisor) throws ExecutionException {
		if (divisor instanceof ValString){
			throw new ExecutionException ("Can't divide by a string");
		} else if (divisor instanceof ValArray){
			throw new ExecutionException ("Can't divide by an array");
		}
		return getNumberInternal(value / divisor.value());
	}
	

	@Override
	public Primitive module (Value divisor) throws ExecutionException {
		if (divisor instanceof ValString){
			throw new ExecutionException ("Can't divide by a string");
		} else if (divisor instanceof ValArray){
			throw new ExecutionException ("Can't divide by an array");
		}
		return getNumberInternal(value % divisor.value());
	}
	
	
	@Override
	public Primitive power (Value exponent) throws ExecutionException {
		if (exponent instanceof ValString){
			throw new ExecutionException ("Can't use a string as exponent");
		} else if (exponent instanceof ValString){
			throw new ExecutionException ("Can't use an array as exponent");
		}
		return getNumberInternal((float) Math.pow(value, exponent.value()));
	}
	
	
	@Override
	public Primitive negate () {
		return getNumberInternal(-value);
	}
	
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		if (other instanceof ValNumber){
			return getNumberInternal((int)value & (int)other.value());
		}
		throw new ExecutionException ("Can't perform bitwise between different types");
	}
	
	
	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		if (other instanceof ValNumber){
			return getNumberInternal((int)value & (int)other.value());
		}
		throw new ExecutionException ("Can't perform bitwise between different types");
	}
	
	
	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		return getNumberInternal(~(int)value);
	}
	




	@Override
	public ValBoolean equalTo (Value value) {
		return this.value == value.value() ? True : False;
	}
	

	@Override
	public ValBoolean differentThan (Value value) {
		return this.value != value.value() ? True : False;
	}
	

	@Override
	public ValBoolean lessThan (Value value) {
		return this.value < value.value() ? True : False;
	}

	
	@Override
	public ValBoolean lessEqualThan (Value value) {
		return this.value <= value.value() ? True : False;
	}
	

	@Override
	public ValBoolean greaterThan (Value value) {
		return this.value > value.value() ? True : False;
	}

	
	@Override
	public ValBoolean greaterEqualThan (Value value) {
		return this.value >= value.value() ? True : False;
	}

	
	
	

	@Override
	public float value () {
		return value;
	}

	
	@Override
	public int length(){
		return Utils.valueToString(value).length();
	}

	
	@Override
	public boolean isTrue () {
		return value != 0;
	}


	@Override
	public boolean equals (Object other) {
		return (other instanceof ValNumber) && value == ((ValNumber) other).value;
	}
	

	@Override
	public ValNumber clone (){
		return new ValNumber(value);
	}
	

	@Override
	public String toString(){
		return Utils.valueToString(value);
	}

}
