package expressions;

import java.io.Serializable;

/**
 * Represents an element of an expression
 * @author MarcoForlini
 */
public interface Token extends Serializable {

	/**
	 * The "const" keyword
	 */
	Token CONST = new Token(){
		private static final long serialVersionUID = -3891676043565611933L;
	};
	//TODO
}
