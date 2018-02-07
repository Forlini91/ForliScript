package expressions;

import compiler.ExecutionException;


/**
 * Represents the supported operators between data
 *
 * @author MarcoForlini
 */
public enum Operator implements Token {

	/** Sum/Concatenation */
	sum ("+", 30, (Value lValue, Value rValue) -> {
		return lValue.add (rValue);
	}),

	/** Difference */
	diff ("-", 30, (Value lValue, Value rValue) -> {
		try {
			return lValue.subtract (rValue);
		} catch (IndexOutOfBoundsException e) {
			throw new ExecutionException ("Can't access element " + rValue.getPrimitive (), e);
		}
	}),

	/** Multiplication/Repetition */
	mult ("*", 31, (Value lValue, Value rValue) -> {
		return lValue.multiply (rValue);
	}),

	/** Division */
	div ("/", 31, (Value lValue, Value rValue) -> {
		return lValue.divide (rValue);
	}),

	/** Rest of division */
	mod ("%", 31, (Value lValue, Value rValue) -> {
		return lValue.module (rValue);
	}),

	/** nth power of value */
	power ("^", 32, (Value lValue, Value rValue) -> {
		return lValue.power (rValue);
	}),

	/** Opposite of value / Reverse of string */
	neg ("-", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.negate ();
	}),

	/** Bitwise and of Numbers / Common chars of Strings / Common elements of both Arrays */
	bitAnd ("&", 25, (Value lValue, Value rValue) -> {
		return lValue.bitwiseAnd (rValue);
	}),

	/** Bitwise or of Numbers / All chars in both Strings / All elements in both Arrays */
	bitOr ("|", 25, (Value lValue, Value rValue) -> {
		return lValue.bitwiseOr (rValue);
	}),

	/** Bitwise not of Number / All missing chars in String */
	bitNot ("~", 25, (Value lValue) -> {
		return lValue.bitwiseNot ();
	}),





	/** Equal */
	equalTo ("==", 20, (Value lValue, Value rValue) -> {
		return lValue.equalTo (rValue);
	}),

	/** Different */
	differentThan ("!=", 20, (Value lValue, Value rValue) -> {
		return lValue.differentThan (rValue);
	}),

	/** Less */
	lessThan ("<", 20, (Value lValue, Value rValue) -> {
		return lValue.lessThan (rValue);
	}),

	/** Less or Equal */
	lessEqualsThan ("<=", 20, (Value lValue, Value rValue) -> {
		return lValue.lessEqualThan (rValue);
	}),

	/** Greater */
	greaterThan (">", 20, (Value lValue, Value rValue) -> {
		return lValue.greaterThan (rValue);
	}),

	/** Greater or Equal */
	greaterEqualsThan (">=", 20, (Value lValue, Value rValue) -> {
		return lValue.greaterEqualThan (rValue);
	}),





	/** Logical Not/Empty string */
	not ("!", 40, (UnaryOperation) (Value rValue) -> {
		return ValBoolean.getBool (!rValue.isTrue ());
	}),

	/** Logic And */
	and ("&&", 11, (Value lValue, Value rValue) -> {
		return ValBoolean.getBool (lValue.isTrue () && rValue.isTrue ());
	}),

	/** Logic Or */
	or ("||", 10, (Value lValue, Value rValue) -> {
		return ValBoolean.getBool (lValue.isTrue () || rValue.isTrue ());
	}),





	/** Assign the value */
	assign ("=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableValue) {
			return ((VariableValue) lValue).set (rValue);
		}
		throw new EvaluationException ("The left expression is not assignable");
	}),

	/** Sum and assign the result */
	sumAndAssign ("+=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).addAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Subtract and assign the result */
	diffAndAssign ("-=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).subtractAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Multiply and assign the result */
	multAndAssign ("*=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).multiplyAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Divide and assign the result */
	divAndAssign ("/=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).divideAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Divide and assign the remaining */
	modAndAssign ("%=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).moduleAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Elevate and assign the result */
	powerAndAssign ("^=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).powerAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Apply bitwise and and assign the result */
	bitAndAndSet ("&=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).bitwiseAndAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),

	/** Apply bitwise or and assign the result */
	bitOrAndSet ("|=", 0, (Value lValue, Value rValue) -> {
		if (lValue instanceof VariableContent) {
			return ((VariableContent) lValue).bitwiseOrAndSet (rValue);
		}
		throw new EvaluationException ("The left expression's content is not assignable");
	}),





	/** Check if the assignable is assigned or null */
	isSet ("?", 40, (UnaryOperation) (Value rValue) -> {
		if (rValue instanceof Variable) {
			return ((Variable) rValue).isSet ();
		}
		throw new EvaluationException ("The left expression is not a assignable!");
	}),

	/** Check if it's a number (or can be converted to number) */
	isNumber ("#?", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.isNumber ();
	}),

	/** Check if it's a string */
	isString ("$?", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.isString ();
	}),

	/** Check if it's an array */
	isArray ("£?", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.isArray ();
	}),

	/** Convert to number */
	toNumber ("#", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.convertToNumber ();
	}),

	/** Convert to string */
	toString ("$", 40, (UnaryOperation) (Value rValue) -> {
		return rValue.convertToString ();
	}),

	/** Convert to array */
	toArray ("£", 40, (UnaryOperation) (Value rValue) -> {
		ValArray va = new ValArray (1);
		va.set (0, rValue);
		return va;
	}),

	/** Extract element at the given position */
	elementAt ("[]", 45, (Value lValue, Value rValue) -> {
		if (lValue instanceof SubValueRead) {
			return ((SubValueRead) lValue).get ((int) rValue.value ());
		}
		throw SubValueRead.accessSubValueException.apply (lValue);
	}),

	/** Do nothing. Just ignore everything after it */
	comment ("//", 0, (UnaryOperation) null)





	;

	/** Name */
	public final String				name;
	/** Priority */
	public final int				priority;
	/** Operator code */
	public final UnaryOperation		unaryOp;
	/** Operator code */
	public final BinaryOperation	binaryOp;


	private Operator (String name, int priority, UnaryOperation unaryOp) {
		this.name = name;
		this.priority = priority;
		this.unaryOp = unaryOp;
		binaryOp = null;
	}

	private Operator (String name, int priority, BinaryOperation binaryOp) {
		this.name = name;
		this.priority = priority;
		this.binaryOp = binaryOp;
		unaryOp = null;
	}



	/**
	 * Attempt to find an operator in the given text at the given position
	 *
	 * @param text The text where to search
	 * @param from The min position
	 * @param to The max position
	 * @return the operator, or null if no operator is found
	 */
	public static Operator parse (String text, int from, int to) {
		if (to - from > 1) {
			switch (text.substring (from, from + 2)) {
				case "==":
					return equalTo;
				case "!=":
					return differentThan;
				case "<=":
					return lessEqualsThan;
				case ">=":
					return greaterEqualsThan;
				case "&&":
					return and;
				case "||":
					return or;
				case "+=":
					return sumAndAssign;
				case "-=":
					return diffAndAssign;
				case "*=":
					return multAndAssign;
				case "/=":
					return divAndAssign;
				case "%=":
					return modAndAssign;
				case "^=":
					return powerAndAssign;
				case "#?":
					return isNumber;
				case "$?":
					return isString;
				case "£?":
					return isArray;
			}
		}
		switch (text.charAt (from)) {
			case '+':
				return sum;
			case '-':
				return diff;
			case '*':
				return mult;
			case '/':
				return div;
			case '%':
				return mod;
			case '^':
				return power;
			case '<':
				return lessThan;
			case '>':
				return greaterThan;
			case '!':
				return not;
			case '?':
				return isSet;
			case '#':
				return toNumber;
			case '$':
				return toString;
			case '£':
				return toArray;
			case '=':
				return assign;
			case '[':
				return elementAt;
		}
		return null;
	}

	@Override
	public String toString () {
		return name;
	}
}
