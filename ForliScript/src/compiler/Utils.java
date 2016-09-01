package compiler;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Contains many useful functions used all across the program
 * @author MarcoForlini
 */
public interface Utils {
	
	/** Scanner used everywhere */
	Scanner scanner = new Scanner(System.in);
	
	/** Random generator */
	Random random = new Random();
	
	/**
	 * Convert a throwable's stack trace to String
	 * @param e		The throwable
	 * @return		Its stack trace
	 */
	static String buildStackTrace(Throwable e){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		e.printStackTrace(ps);
		return new String(baos.toByteArray(), StandardCharsets.UTF_8);
	}
	
	/**
	 * Split the string in chars and return an array of strings
	 * @param text	The string
	 * @return		An array where each element is a string with a char of the string in input
	 */
	static String[] breakStringToArray(String text){
		char[] chars = text.toCharArray();
		int n = chars.length;
		String[] values = new String[n];
		for (int i = 0; i < n; i++){
			values[i] = String.valueOf(chars[i]);
		}
		return values;
	}
	
	/**
	 * Split the string in chars and return an ArrayList of strings
	 * @param text	The string
	 * @return		An ArrayList where each element is a string with a char of the string in input
	 */
	static List<String> breakStringToList(String text){
		char[] chars = text.toCharArray();
		List<String> values = new ArrayList<>(chars.length);
		for (char c : chars){
			values.add(String.valueOf(c));
		}
		return values;
	}
	
	/**
	 * Get the string representation of the given value
	 * @param value	The value
	 * @return		The string representation of the passed value
	 */
	static String valueToString(float value){
		if (value == Math.floor(value)){
			return Integer.toString((int) value);
		}
		return Float.toString(value);
	}
	
	/**
	 * Reverse the characters in the string in input
	 * @param str	The string
	 * @return		The reversed string
	 */
	static String reverseString(String str){
		return new StringBuilder(str).reverse().toString();
	}
	
	/**
	 * Concatenate the string with itself as many times as the given number
	 * Doesn't change the original String (Due to Java's String implementation)
	 * @param str	The string
	 * @param num	Number of repetition
	 * @return		The concatenation of the string with itself
	 */
	static String repeatString(String str, int num){
		StringBuilder sb = new StringBuilder(str.length()*num);
		while (num-- >= 0){
			sb.append(str);
		}
		return sb.toString();
	}

	
	/**
	 * Concatenate the array with itself as many times as the given number.
	 * It changes the passed array.
	 * @param <T>	The type of elements in the List
	 * @param arr	The array
	 * @param num	Number of repetition
	 * @return		The concatenation of the array with itself
	 */
	static <T> List<T> repeatArray(List<T> arr, int num){
		List<T> clone = new ArrayList<>(arr);
		while (num-- >= 0){
			arr.addAll(clone);
		}
		return arr;
	}
	
	
	/**
	 * Check if the string is a number
	 * @param str	The string
	 * @return		true if it's a number
	 */
	static boolean isNumber(String str){
		try{
			Float.parseFloat(str);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * Convert the string to number
	 * @param str	The string
	 * @return		The number
	 * @throws NumberFormatException	If the string is not a number
	 */
	static Float toNumber(String str) throws NumberFormatException {
		return Float.parseFloat(str);
	}


	/**
	 * Get the number of digits of an integer number
	 * @param number	The number
	 * @return			The number of digits in the given number
	 */
	static int sizeNumber(int number){
		return (int) Math.log10(number) + 1;
	}
}
