package test;

import java.io.IOException;
import java.util.List;

import compiler.CompilatorException;
import compiler.Compiler;
import compiler.ExecutionException;
import compiler.FileManager;
import compiler.Program;
import compiler.Utils;


/**
 * Main class
 *
 * @author MarcoForlini
 */
public class Test {

	/**
	 * Main... what else?
	 *
	 * @param args Unused args...
	 */
	public static void main (String[] args) {
		try {
			Program program;
			String filePath;
			if (args.length > 0 && args[0].length () > 0) {
				filePath = args[0];
			} else {
				System.out.println ("Insert the name of a file to load (leave blank to load a the test .fosc file in the package)");
				filePath = Utils.scanner.nextLine ();
			}
			if (filePath == null || filePath.isEmpty ()) {
				filePath = "TestProgram.fosc";
			}
			boolean isSource = filePath.endsWith (".fosc"), isProgram = filePath.endsWith (".foscp");

			if (!isSource && !isProgram) {
				System.out.println ("Unrecognized file format!");
				// goto GETFILE;
			}

			if (isSource) {
				System.out.println ("Read source code from file...");
				List <String> sourceCode = FileManager.readSource (filePath);
				System.out.println ("Compile source code...");
				program = Compiler.compile (sourceCode);
				try {
					System.out.println ("Save program to file...");
					FileManager.saveProgram (filePath + 'p', program);
				} catch (IOException e) {
					System.out.println (e.getMessage ());
				}
			} else {
				System.out.println ("Read program from file...");
				program = FileManager.readProgram (filePath);
			}

			program.setVariable ("someVar1", "Mannaggia i sassi!");
			program.setVariable ("someVar2", 1762);
			System.out.println ("Run program\n\n\n");
			program.run ();
		} catch (IOException | CompilatorException | ExecutionException | ClassNotFoundException e) {
			e.printStackTrace ();
		}
	}

}
