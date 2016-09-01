package expressions;

import compiler.ExecutionException;

/**
 * Represents a value
 * @author MarcoForlini
 */
public class ValNull implements Constant {
	
	private static final long serialVersionUID = -5497381123807942997L;
	
	/** A String with text "&lt;null&gt;" */
	private static final ValString NullString = new ValString("<null>");

	/** The NullPointerException */
	private static final NullPointerException nullPointerException = new NullPointerException ("Value is null");

	/** The object NULL */
	public static final ValNull objNull = new ValNull();
	
	

	/** Only allow one NULL object */
	private ValNull (){}
	

	@Override
	public ValNull eval () {
		return this;
	}
	
	
	@Override
	public ValNull getPrimitive () {
		return this;
	}
	
	



	@Override
	public ValBoolean isNumber () {
		throw nullPointerException;
	}
	

	@Override
	public ValBoolean isString () {
		throw nullPointerException;
	}
	
	
	@Override
	public ValBoolean isArray () {
		throw nullPointerException;
	}
	

	@Override
	public ValNumber convertToNumber () {
		throw nullPointerException;
	}


	@Override
	public ValString convertToString () {
		return NullString;
	}
	

	@Override
	public ValNumber floor () {
		throw nullPointerException;
	}

	
	@Override
	public ValNumber ceil () {
		throw nullPointerException;
	}
	
	
	@Override
	public ValNumber round () {
		throw nullPointerException;
	}

	
	@Override
	public ValString trim () {
		throw nullPointerException;
	}
	
	

	

	@Override
	public Primitive add (Value add) {
		throw nullPointerException;
	}


	@Override
	public Primitive subtract (Value subtract) {
		throw nullPointerException;
	}
	
	
	@Override
	public Primitive multiply (Value multiplier) {
		throw nullPointerException;
	}
	
	
	@Override
	public Primitive divide (Value divisor) {
		throw nullPointerException;
	}


	@Override
	public Primitive module (Value divisor) {
		throw nullPointerException;
	}


	@Override
	public Primitive power (Value exponent) {
		throw nullPointerException;
	}


	@Override
	public Primitive negate () {
		throw nullPointerException;
	}
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		throw nullPointerException;
	}
	
	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		throw nullPointerException;
	}
	
	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		throw nullPointerException;
	}

	
	
	

	@Override
	public ValBoolean equalTo (Value other) {
		return this == other ? True : False;
	}


	@Override
	public ValBoolean differentThan (Value other) {
		return this == other ? True : False;
	}
	

	@Override
	public ValBoolean lessThan (Value other) {
		throw nullPointerException;
	}


	@Override
	public ValBoolean lessEqualThan (Value other) {
		throw nullPointerException;
	}

	
	@Override
	public ValBoolean greaterThan (Value other) {
		throw nullPointerException;
	}

	
	@Override
	public ValBoolean greaterEqualThan (Value other) {
		throw nullPointerException;
	}
	

	
	
	
	@Override
	public float value () {
		throw nullPointerException;
	}
	
	
	@Override
	public int length () {
		throw nullPointerException;
	}


	@Override
	public boolean isTrue() {
		return false;
	}
	
	
	@Override
	public boolean equals (Object other) {
		return other == Null;
	}
	

	@Override
	public ValNull clone (){
		return this;
	}
	
	
	@Override
	public String toString(){
		return NullString.toString();
	}

}
