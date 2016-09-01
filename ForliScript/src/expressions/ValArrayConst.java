package expressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import compiler.ExecutionException;
import compiler.Utils;

/**
 * Represents a value
 * @author MarcoForlini
 */
public class ValArrayConst extends ValArray implements Constant {
	
	private static final long serialVersionUID = -3348506363493769885L;

	
	
	/**
	 * Create a new {@link ValArrayConst}
	 * @param val	The value
	 */
	public ValArrayConst (int val){
		super(val);
	}


	/**
	 * Create a new {@link ValArrayConst}
	 * @param value		The number of elements
	 * @throws EvaluationException	If an error occur while getting the value
	 */
	public ValArrayConst (Primitive value) throws EvaluationException {
		this((int) value.value());
	}


	/**
	 * Create a new {@link ValArrayConst}
	 * @param values	The values
	 */
	public ValArrayConst (List<Primitive> values){
		super(values);
	}



	@Override
	public ValArray getPrimitive(){
		return clone();
	}
	
	
	
	/**
	 * Add the given element to this array
	 */
	@Override
	public Primitive add (Value add) throws ExecutionException {
		List<Primitive> clone = new ArrayList<>(getElements());
		if (add instanceof ValArray){
			clone.addAll(((ValArray) add).getElements());
		} else {
			clone.add(add.getPrimitive());
		}
		if (add instanceof Constant){
			return new ValArrayConst(clone);
		}
		return new ValArray(clone);
	}


	/**
	 * If subtract is an Array, remove from this array all elements in that array
	 * If subtract is a Number, remove from this array the element at position (subtract.value())
	 */
	@Override
	public Primitive subtract (Value subtract) throws EvaluationException {
		List<Primitive> clone = new ArrayList<>(getElements());
		if (subtract instanceof ValNumber) {
			clone.remove((int) subtract.value());
			if (subtract instanceof Constant){
				return new ValArrayConst(clone);
			}
			return new ValArray(clone);
		}
		throw new EvaluationException("Can only subtract an array or an index from an array");
	}
	
	
	@Override
	public Primitive multiply (Value multiplier) throws EvaluationException {
		if (multiplier instanceof ValNumber){
			float mult = multiplier.value();
			if (mult == 1 && multiplier instanceof Constant){
				return this;
			}
			return new ValArray(Utils.repeatArray(new ArrayList<>(getElements()), (int) mult));
		}
		throw new EvaluationException("Can't multiplicate an array without anything but a number");
	}
	
	
	@Override
	public Primitive divide (Value divisor) throws EvaluationException {
		if (divisor instanceof ValArray){
			List<Primitive> clone = new ArrayList<>(getElements());
			if (clone.removeAll(((ValArray) divisor).getElements())){
				return new ValArray(clone);
			} else if (divisor instanceof Constant){
				return this;
			}
			return clone();
		}
		throw new EvaluationException("Can't divide an Array by another type");
	}


	@Override
	public Primitive module (Value divisor) throws ExecutionException {
		if (divisor instanceof ValArray){
			ValArray arrDivisor = (ValArray) divisor;
			List<Primitive> clone1 = new ArrayList<>(getElements());
			List<Primitive> clone2 = new ArrayList<>(arrDivisor.getElements());
			clone1.removeAll(arrDivisor.getElements());
			clone2.removeAll(getElements());
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
		List<Primitive> values = getElements();
		if (values.isEmpty()){
			return this;
		}
		List<Primitive> clone = new ArrayList<>(values);
		Collections.reverse(clone);
		return new ValArrayConst(clone);
	}
	
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			List<Primitive> clone = new ArrayList<>(getElements());
			if (clone.retainAll(((ValArray)other).getElements())){
				return new ValArray(clone);
			} else if (other instanceof Constant) {
				return this;
			}
			return clone();
		}
		throw new EvaluationException("Can't apply bitwise and between an Array and another type");
	}


	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		if (other instanceof ValArray){
			List<Primitive> values = getElements();
			Set<Primitive> clone = new HashSet<>(values);
			if (clone.addAll(((ValArray)other).getElements()) || clone.size() != values.size()){	//If it added new elements or removed redundant elements...
				return new ValArrayConst(new ArrayList<>(clone));
			} else if (other instanceof Constant) {
				return this;
			}
			return clone();
		}
		throw new EvaluationException("Can't apply bitwise or between an Array and another type");
	}
	

	@Override
	public ValArray clone (){
		return super.clone();
	}
	
}
