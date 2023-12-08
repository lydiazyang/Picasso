package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.Assignment;
import picasso.parser.tokens.Token;
import picasso.parser.language.expressions.Variable;

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
		// Process the Parameters
		ExpressionTreeNode rhsExpression = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		// Get the variable name
		Variable variable = new Variable(tokens.pop().toString());
		
		return new Assignment(variable, rhsExpression);
	}

}
