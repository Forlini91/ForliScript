package expressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import compiler.ExecutionException;
import compiler.Utils;

/**
 * Represents a value
 * @author MarcoForlini
 */
public class ValArray implements Primitive, VariableContent, SubValue {
	
	private static final long serialVersionUID = -3348506363493769885L;

	private static final ExecutionException notComparableException = new ExecutionException("Array can't be compared with other types");

	
	
	/** Values contained in this array */
	private final List<Primitive> elements;
	
	
	
	/**
	 * Create a new {@link ValArray}
	 * @param val	The value
	 */
	public ValArray (int val){
		elements = new ArrayList<>(val);
	}


	/**
	 * Create a new {@link ValArray}
	 * @param value		The number of elements
	 * @throws EvaluationException	If an error occur while getting the value
	 */
	public ValArray (Primitive value) throws EvaluationException {
		this((int) value.value());
	}


	/**
	 * Create a new {@link ValArray}
	 * @param values	The values
	 */
	public ValArray (List<Primitive> values){
		elements = values;
	}
	
	
	
	/**
	 * Gets the values of this Array. Any change to this value are applied to the array.
	 * @return	A list with the values of this array.
	 */
	public List<Primitive> getElements(){
		return elements;
	}
	
	
	
	/**
	 * Get the value at index i
	 * @param i	The index
	 * @return	The value
	 * @throws EvaluationException	If an error occur while executing the operation
	 */
	@Override
	public ArrayElement get(int i) throws EvaluationException {
		try{
			return new ArrayElement(elements.get(i), (Value v)->set(i,v));
		} catch (IndexOutOfBoundsException e){
			throw new EvaluationException ("Can't access element at index " + i, e);
		}
	}
	
	
	/**
	 * Set the value at index i
	 * @param i		The index
	 * @param value The value to add
	 * @throws ExecutionException	If trying to assign a value to a constant variable
	 */
	@Override
	public Primitive set(int i, Value value) throws ExecutionException {
		Primitive prim = value.getPrimitive();
		if (i == elements.size()){
			elements.add(prim);
		} else {
			try{
				elements.set(i, prim);
			} catch (IndexOutOfBoundsException e){
				throw new EvaluationException ("Can't access element " + i, e);
			}
		}
		return prim;
	}

	/**
	 * Erase the value at index i
	 * @param i	The index
	 * @return	The erased element
	 * @throws EvaluationException	If an error occur while executing the operation
	 */
	public Value erase(int i) throws EvaluationException {
		try{
			return elements.remove(i);
		} catch (IndexOutOfBoundsException e){
			throw new EvaluationException ("Can't access element " + i, e);
		}
	}





	@Override
	public Primitive addAndSet (Value add) throws ExecutionException {
		if (add instanceof ValArray){
			elements.addAll(((ValArray) add).elements);
		} else {
			elements.add(add.getPrimitive());
		}
		return this;
	}

	@Override
	public Primitive subtractAndSet (Value subtract) throws ExecutionException {
		if (subtract instanceof ValArray){
			elements.removeAll(((ValArray) subtract).elements);
			return this;
		} else if (subtract instanceof ValNumber) {
			elements.remove((int) subtract.value());
			return this;
		}
		throw new EvaluationException("Can only subtract an array or an index from an array");
	}

	@Override
	public Primitive multiplyAndSet (Value multiplier) throws ExecutionException {
		if (multiplier instanceof ValNumber){
			Utils.repeatArray(elements, (int) multiplier.value());
			return this;
		}
		throw new EvaluationException("Can't multiplicate an array without anything but a number");
	}

	@Override
	public Primitive divideAndSet (Value divisor) throws ExecutionException {
		throw new EvaluationException("Can't divide an Array");
	}

	@Override
	public Primitive moduleAndSet (Value divisor) throws ExecutionException {
		throw new EvaluationException("Can't divide an Array");
	}

	@Override
	public Primitive powerAndSet (Value exponent) throws ExecutionException {
		throw new EvaluationException("Can't aplpy power to an Array");
	}

	@Override
	public Primitive bitwiseAndAndSet (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			elements.retainAll(((ValArray)other).elements);
			return this;
		}
		throw new EvaluationException("Can't apply bitwise and between an Array and another type");
	}

	@Override
	public Primitive bitwiseOrAndSet (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			Set<Primitive> clone = new HashSet<>(elements);
			elements.addAll(((ValArray) other).elements.parallelStream().filter(x -> !clone.contains(x)).collect(Collectors.toList()));
			return this;
		}
		throw new EvaluationException("Can't apply bitwise or between an Array and another type");
	}





	/**
	 * Add the given element to this array
	 */
	@Override
	public Primitive add (Value add) throws ExecutionException {
		List<Primitive> clone = new ArrayList<>(elements);
		clone.add(add.getPrimitive());
		return new ValArray(clone);
	}


	/**
	 * If subtract is an Array, remove from this array all elements in that array
	 * If subtract is a Number, remove from this array the element at position (subtract.value())
	 */
	@Override
	public Primitive subtract (Value subtract) throws EvaluationException {
		List<Primitive> clone = new ArrayList<>(elements);
		if (subtract instanceof ValNumber) {
			clone.remove((int) subtract.value());
			return new ValArray(clone);
		}
		throw new EvaluationException("Can only subtract an array or an index from an array");
	}
	
	
	@Override
	public Primitive multiply (Value multiplier) throws EvaluationException {
		if (multiplier instanceof ValNumber){
			float mult = multiplier.value();
			if (mult == 1){
				return this;
			}
			return new ValArray(Utils.repeatArray(new ArrayList<>(elements), (int) mult));
		}
		throw new EvaluationException("Can't multiplicate an array without anything but a number");
	}
	
	
	@Override
	public Primitive divide (Value divisor) throws EvaluationException {
		if (divisor instanceof ValArray){
			List<Primitive> clone = new ArrayList<>(elements);
			if (clone.removeAll(((ValArray) divisor).elements)){
				return new ValArray(clone);
			}
			return this;
		}
		throw new EvaluationException("Can't divide an Array by another type");
	}


	@Override
	public Primitive module (Value divisor) throws ExecutionException {
		if (divisor instanceof ValArray){
			ValArray arrDivisor = (ValArray) divisor;
			List<Primitive> clone1 = new ArrayList<>(elements);
			List<Primitive> clone2 = new ArrayList<>(arrDivisor.elements);
			clone1.removeAll(arrDivisor.elements);
			clone2.removeAll(elements);
			clone1.addAll(clone2);
			return new ValArray(clone1);
		}
		throw new ExecutionException("Can't divide an Array by another type");
	}


	@Override
	public Primitive power (Value exponent) throws EvaluationException {
		throw new EvaluationException("Can't apply power to an array");
	}


	@Override
	public Primitive negate () throws EvaluationException {
		if (elements.isEmpty()){
			return this;
		}
		List<Primitive> clone = new ArrayList<>(elements);
		Collections.reverse(clone);
		return new ValArray(clone);
	}
	
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			List<Primitive> clone = new ArrayList<>(elements);
			if (clone.retainAll(((ValArray)other).elements)){
				return new ValArray(clone);
			}
			return this;
		}
		throw new EvaluationException("Can't apply bitwise and between an Array and another type");
	}


	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			Set<Primitive> clone = new HashSet<>(elements);
			if (clone.addAll(((ValArray)other).elements) || clone.size() != elements.size()){	//If it added new elements or removed redundant elements...
				return new ValArray(new ArrayList<>(clone));
			}
			return this;
		}
		throw new EvaluationException("Can't apply bitwise or between an Array and another type");
	}
	

	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		int size = elements.size();
		List<Primitive> clone = new ArrayList<>(size);
		for (Primitive primitive : elements){
			clone.add(primitive.bitwiseNot());
		}
		return new ValArray(clone);
	}
	
	
	
	
	
	@Override
	public ValBoolean equalTo (Value value) {
		return this == value || (value instanceof ValArray && elements.equals(((ValArray)value).elements)) ? True : False;
	}


	@Override
	public ValBoolean differentThan (Value value) {
		return this == value || (value instanceof ValArray && elements.equals(((ValArray)value).elements)) ? False : True;
	}
	

	@Override
	public ValBoolean lessThan (Value value) throws ExecutionException {
		if (value instanceof ValArray){
			return ((ValArray) value).elements.containsAll(elements) && !elements.containsAll(((ValArray) value).elements) ? True : False;
		}
		throw notComparableException;
	}


	@Override
	public ValBoolean lessEqualThan (Value value) throws ExecutionException {
		if (value instanceof ValArray){
			return ((ValArray) value).elements.containsAll(elements) ? True : False;
		}
		throw notComparableException;
	}

	
	@Override
	public ValBoolean greaterThan (Value value) throws ExecutionException {
		if (value instanceof ValArray){
			return elements.containsAll(((ValArray) value).elements) && !((ValArray) value).elements.containsAll(elements) ? True : False;
		}
		throw notComparableException;
	}

	
	@Override
	public ValBoolean greaterEqualThan (Value value) throws ExecutionException {
		if (value instanceof ValArray){
			return elements.containsAll(((ValArray) value).elements) ? True : False;
		}
		throw notComparableException;
	}
	




	@Override
	public ValNumber floor () throws EvaluationException {
		throw new EvaluationException("Can't apply floor to an array");
	}
	

	@Override
	public ValNumber ceil () throws EvaluationException {
		throw new EvaluationException("Can't apply ceil to an array");
	}


	@Override
	public ValNumber round () throws EvaluationException {
		throw new EvaluationException("Can't apply round to an array");
	}
	

	@Override
	public ValString trim () throws EvaluationException {
		throw new EvaluationException("Can't apply trim to an array");
	}




	@Override
	public ValBoolean isNumber () {
		return False;
	}
	
	
	@Override
	public ValBoolean isString () {
		return False;
	}
	

	@Override
	public ValBoolean isArray () {
		return True;
	}

	
	@Override
	public ValNumber convertToNumber () throws EvaluationException {
		return ValNumber.getNumber(elements.size());
	}
	
	
	@Override
	public ValString convertToString () {
		return new ValString(Utils.valueToString(elements.size()));
	}
	
	


	
	@Override
	public float value () {
		return hashCode();
	}
	

	@Override
	public int length () {
		return elements.size();
	}


	@Override
	public boolean isTrue() {
		return !elements.isEmpty();
	}
	

	@Override
	public boolean equals (Object other) {
		return (other instanceof ValArray) && elements == ((ValArray) other).elements;
	}


	@Override
	public ValArray clone (){
		return new ValArray(new ArrayList<>(elements));
	}


	@Override
	public String toString(){
		return elements.toString();
	}
	
}
