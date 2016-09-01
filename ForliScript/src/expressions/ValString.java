package expressions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import compiler.ExecutionException;
import compiler.Utils;

/**
 * Represents the primitive type String
 * @author MarcoForlini
 */
public class ValString implements Constant, SubValueRead {

	private static final long serialVersionUID = -6450699613440471062L;


	/** An empty string */
	public static final ValString objEmpty = new ValString("");



	/** The text of this {@link ValString} */
	private String text;

	

	/**
	 * Creates a new {@link ValString}
	 * @param text	The initial text
	 */
	public ValString (String text){
		this.text = text;
	}

	
	
	
	
	@Override
	public Primitive get (int i) throws EvaluationException {
		try {
			return new ValString(Character.toString(text.charAt(i)));
		} catch (IndexOutOfBoundsException e){
			throw new EvaluationException ("Can't extract character at index " + i, e);
		}
	}





	@Override
	public ValBoolean isNumber () {
		if (Utils.isNumber(text)){
			return True;
		}
		return False;
	}
	

	@Override
	public ValBoolean isString () {
		return True;
	}
	
	
	@Override
	public ValBoolean isArray () {
		return False;
	}
	

	@Override
	public ValNumber convertToNumber () throws EvaluationException {
		return ValNumber.getNumber(Utils.toNumber(text));
	}


	@Override
	public ValString convertToString () {
		return this;
	}


	@Override
	public ValNumber floor () throws EvaluationException {
		throw new EvaluationException("Can't apply floor to a string");
	}

	
	@Override
	public ValNumber ceil () throws EvaluationException {
		throw new EvaluationException("Can't apply ceil to a string");
	}
	
	
	@Override
	public ValNumber round () throws EvaluationException {
		throw new EvaluationException("Can't apply round to a string");
	}

	
	@Override
	public ValString trim () throws EvaluationException {
		return new ValString(text.trim());
	}

	
	
	

	/**
	 * Concatenate the string and the value
	 * @return A new string which is the concatenation of this string with the given value
	 */
	@Override
	public Primitive add (Value add) {
		return new ValString (text + add);
	}
	
	
	/**
	 * Only ValNumber | Truncate the last (subtract.value()) chars from the string
	 * @return A new string without the last (multiplier.value()) chars
	 */
	@Override
	public Primitive subtract (Value subtract) throws EvaluationException {
		if (subtract instanceof ValString){
			throw new EvaluationException("Can't subtract a string from another string");
		} else if (subtract instanceof ValArray){
			throw new EvaluationException("Can't subtract anarray from a string");
		}
		int newLength = (int) (text.length() - subtract.value());
		if (newLength > 0){
			return new ValString(text.substring(0, newLength));
		}
		return objEmpty;
	}


	/**
	 * Only ValNumber | Repeat the string (multiplier.value()) times
	 * @return A new string which is the repetition of this string for (multiplier.value()) times
	 */
	@Override
	public Primitive multiply (Value multiplier) throws EvaluationException {
		if (multiplier instanceof ValString){
			throw new EvaluationException("Can't multiply a String with another String");
		} else if (multiplier instanceof ValArray){
			throw new EvaluationException("Can't multiply a String with an Array");
		}
		return new ValString(Utils.repeatString(text, (int) multiplier.value()));
	}


	/**
	 * Only ValNumber | Truncate the first (multiplier.value()) chars from the string
	 * @return A new string without the first (multiplier.value()) chars
	 */
	@Override
	public Primitive divide (Value divisor) throws EvaluationException {
		if (divisor instanceof ValString){
			throw new EvaluationException("Can't truncate a string from another String");
		} else if (divisor instanceof ValArray){
			throw new EvaluationException("Can't truncate a string from an Array");
		}
		int newStart = (int) divisor.value();
		if (newStart < text.length()){
			return new ValString(text.substring(newStart));
		}
		return objEmpty;
	}
	
	
	@Override
	public Primitive module (Value divisor) throws EvaluationException {
		throw new EvaluationException("Can't apply module to a String");
	}
	
	
	@Override
	public Primitive power (Value exponent) throws EvaluationException {
		throw new EvaluationException("Can't apply power to a String");
	}
	
	
	/**
	 * Reverse the string
	 */
	@Override
	public Primitive negate () {
		return new ValString(Utils.reverseString(text));
	}
	
	
	@Override
	public Primitive bitwiseAnd (Value other) throws ExecutionException {
		if (other instanceof ValString){
			String otherText = ((ValString) other).text;
			int i = 0;
			char[] chars;
			int l1 = text.length(), l2 = otherText.length();
			if (l1 >= l2){
				chars = new char[l1];
				for (char c : text.toCharArray()){
					if (otherText.indexOf(c) >= 0){
						chars[i++] = c;
					}
				}
			} else {
				chars = new char[l1];
				for (char c : otherText.toCharArray()){
					if (text.indexOf(c) >= 0){
						chars[i++] = c;
					}
				}
			}
			Arrays.sort(chars);
			return new ValString(new String(chars));
		}
		throw new EvaluationException("Can't apply bitwise and between a String and another type");
	}
	
	
	@Override
	public Primitive bitwiseOr (Value other) throws ExecutionException {
		if (other instanceof ValString){
			String otherText = ((ValString) other).text;
			Set<Character> charSet = new HashSet<>();
			for (char c : text.toCharArray()){
				charSet.add(c);
			}
			for (char c : otherText.toCharArray()){
				charSet.add(c);
			}
			Character[] Chars = charSet.toArray(new Character[charSet.size()]);
			char[] chars = new char[Chars.length];
			for (int i = 0; i < chars.length; i++){
				chars[i] = Chars[i];
			}
			Arrays.sort(chars);
			return new ValString(new String(chars));
		}
		throw new EvaluationException("Can't apply bitwise or between a String and another type");
	}
	
	
	@Override
	public Primitive bitwiseNot () throws ExecutionException {
		throw new EvaluationException("Can't apply bitwise not on a String");
	}
	
	


	
	@Override
	public ValBoolean equalTo (Value value) {
		return text.equals(value.toString()) ? True : False;
	}
	
	
	@Override
	public ValBoolean differentThan (Value value) {
		return text.equals(value.toString()) == false ? True : False;
	}

	
	@Override
	public ValBoolean lessThan (Value value) {
		return text.compareTo(value.toString()) < 0 ? True : False;
	}
	
	
	@Override
	public ValBoolean lessEqualThan (Value value) {
		return text.compareTo(value.toString()) <= 0 ? True : False;
	}
	

	@Override
	public ValBoolean greaterThan (Value value) {
		return text.compareTo(value.toString()) > 0 ? True : False;
	}
	

	@Override
	public ValBoolean greaterEqualThan (Value value) {
		return text.compareTo(value.toString()) >= 0 ? True : False;
	}
	




	@Override
	public float value () {
		return hashCode();
	}
	

	@Override
	public int length () {
		return text.length();
	}
	
	
	@Override
	public boolean isTrue() {
		return !text.isEmpty();
	}


	@Override
	public boolean equals (Object other) {
		return (other instanceof ValString) && text.equals(((ValString) other).text);
	}
	

	@Override
	public ValString clone (){
		return new ValString(text);
	}


	@Override
	public String toString(){
		return text;
	}

}
