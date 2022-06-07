package ca.concordia.soen6591.assignment2.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import ca.concordia.soen6591.assignment2.detecters.StaticAnalyzer;

public class CUParser {

	public CompilationUnit parseCU(String filePath) {
		String fileContent;
		CompilationUnit parsedUnit = null;
		try {
			fileContent = readFileToString(filePath);
			parsedUnit = parse(fileContent);
			return parsedUnit;
		} catch (Exception e) {
			StaticAnalyzer.logger.fatal(e.getCause() + " exception happned! in class " + filePath.toString() + " because the class is abstract", e);
		}
		
		return parsedUnit;
	}

	// parse java file content
	private CompilationUnit parse(String str) {
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(str.toCharArray());
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setStatementsRecovery(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}

	// read file content into a string
	private String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return fileData.toString();
	}

}
