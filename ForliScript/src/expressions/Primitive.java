package expressions;

/**
 * Represents a constant
 * @author MarcoForlini
 */
public interface Primitive extends Value {
	
	/**
	 * Get this primitive
	 * @return	this primitive
	 */
	@Override
	default Primitive getPrimitive() {
		return this;
	}
	
	
	@Override
	Primitive clone();
	
}
