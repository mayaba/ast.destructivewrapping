package ca.concordia.soen6591.assignment2.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.ThrowStatement;

import ca.concordia.soen6591.assignment2.detecters.StaticAnalyzer;

public class CatchClauseVisitor extends ASTVisitor {
	private int totalDetected = 0, totalCatchClauses = 0;
	private String compilationUnitName;
	private CompilationUnit compilationUnit;

	public CatchClauseVisitor(String unitName, CompilationUnit compilationUnit) {
		this.compilationUnitName = unitName;
		this.compilationUnit = compilationUnit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(CatchClause node) {
		totalCatchClauses++; // increment catch clause counter
		if (!node.getBody().statements().isEmpty()) { // if the node is not empty
			List<Statement> statements = node.getBody().statements(); // get node statements
			statements.forEach((s) -> {
				if (s instanceof ThrowStatement) { // we're only interested in catch clauses with throw statements
					SimpleName exceptionName = node.getException().getName();
//					System.out.println(exceptionName.toString());
					// visit s: throw statement
					s.accept(new ASTVisitor() {
						@Override
						public boolean visit(SimpleName node) {
//							System.out.println("Visiting " + node.toString());
							if (exceptionName.toString().equals(node.toString())) {
								printDetected(node.getStartPosition());
								totalDetected++;
							}
							return super.visit(node);
						}
					});
				}
			});

		}

		return super.visit(node);
	}

	public int getTotalDetected() {
		return totalDetected;
	}
	
	
	public int getTotalCatchClauses() {
		return totalCatchClauses;
	}

	private void printDetected(int position) {
		StaticAnalyzer.logDetection(compilationUnit, compilationUnitName, position);
	}

}
