package compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class which read source code and can read/save compiled a {@link Program}
 * @author MarcoForlini
 */
public class FileManager {
	
	/**
	 * Read the sourcecode from the file
	 * @param filePath	The file path
	 * @return			A list of strings
	 * @throws IOException	If an any IO problems happens
	 */
	public static List <String> readSource (String filePath) throws IOException {
		List<String> sourceCode = new ArrayList<>();
		File file = new File(filePath);
		try (Scanner scanner = new Scanner(file)){
			while(scanner.hasNextLine()){
				sourceCode.add(scanner.nextLine().trim());
			}
		}
		return sourceCode;
	}
	
	/**
	 * Writes a compiled program to file
	 * @param filePath	The file path
	 * @param program	The program
	 * @throws IOException	If an any IO problems happens
	 */
	public static void saveProgram (String filePath, Program program) throws IOException {
		try (FileOutputStream fileInputStream = new FileOutputStream(filePath);
				ObjectOutputStream objectInputStream = new ObjectOutputStream(fileInputStream)){
			objectInputStream.writeObject(program);
		}
	}
	
	/**
	 * Reads a compiled program from file
	 * @param filePath	The file path
	 * @return			The program
	 * @throws IOException	If an any IO problems happens
	 * @throws ClassNotFoundException	If the files doesn't contains a program
	 */
	public static Program readProgram (String filePath) throws IOException, ClassNotFoundException {
		try (FileInputStream fileInputStream = new FileInputStream(filePath);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
			return (Program) objectInputStream.readObject();
		}
	}

}
