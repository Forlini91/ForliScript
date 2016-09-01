package expressions;


/**
 * A generic function
 * @author MarcoForlini
 */
public interface GenericFunction extends Token {

	/**
	 * @return the name
	 */
	String getName ();


	/**
	 * @return the description
	 */
	String getDescription ();


	/**
	 * @return the minParams
	 */
	int getMinParams ();


	/**
	 * @return the optParams
	 */
	int getOptParams ();


	/**
	 * @return the constantResult
	 */
	boolean isConstantResult ();


	/**
	 * @return the cmdOp
	 */
	FunctionCode getFunctionCode ();

}
