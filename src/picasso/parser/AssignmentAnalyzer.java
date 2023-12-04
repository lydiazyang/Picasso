package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.Assignment;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.Variable;
import picasso.parser.tokens.operations.AssignmentToken;

/**
 * Handles parsing the Assignment function
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * @refactored by Aiden Boeshans
 * 
 */
public class AssignmentAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the assignment token
		// Get the variable name
		String variable = ((Variable) tokens.pop()).getValue();
		// Process the Parameters
		ExpressionTreeNode rhsExpression = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		return new Assignment(variable, rhsExpression, new AssignmentToken());
	}

}
