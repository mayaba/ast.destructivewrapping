package ca.concordia.soen6591.assignment2.detecters;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.core.dom.CompilationUnit;

import ca.concordia.soen6591.assignment2.utility.CUParser;
import ca.concordia.soen6591.assignment2.utility.FileWalker;
import ca.concordia.soen6591.assignment2.visitors.CatchClauseVisitor;

public class StaticAnalyzer {
	static final Logger logger = LogManager.getLogger(StaticAnalyzer.class);
	static final CUParser cuParser = new CUParser();
	static int totalDetected = 0, totalCatchClauses = 0;
	
	public static void logDetection(CompilationUnit compilationUnit, String compilationUnitName, int position) {
		logger.warn("[!] Destructive Warpping Detected in Class: " + compilationUnitName + " at line# " + compilationUnit.getLineNumber(position));
	}
	
	
	public static void main(String[] args) throws IOException {
		
		final String dirPath = args[0];
		final FileWalker fileWalker = new FileWalker(dirPath);
		
		logger.info("Starting the program ...");
		
		List<Path> javaFiles = fileWalker.filewalk();
		for (Path path : javaFiles) {
			try {
				CompilationUnit parsedCU = cuParser.parseCU(path.toString());
				CatchClauseVisitor exceptionVisitor = new CatchClauseVisitor(path.toString(), parsedCU);
				parsedCU.accept(exceptionVisitor);
				totalDetected += exceptionVisitor.getTotalDetected();
				totalCatchClauses += exceptionVisitor.getTotalCatchClauses();
			} catch (Exception e) {
				logger.fatal("Exception happned!", e);
			}
			
		}
		
		logger.info("_____________Summary______________");
		logger.info("Total number of catch clauses: " + totalCatchClauses);
		logger.info("Total number of exception wrapping: " + totalDetected);
		logger.info("Exsiting the program ...");
	}

}
