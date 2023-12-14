package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.Assignment;
import picasso.parser.tokens.IdentifierToken;
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
		// Print tokens
		System.out.println(tokens);
		tokens.pop(); // Remove the assignment token
		// Process the Parameters
		ExpressionTreeNode rhsExpression = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);
		// Check that LHS is an identifier token
		if (tokens.peek() instanceof IdentifierToken) {
			IdentifierToken t = (IdentifierToken) tokens.peek();
			if (t.getName().equals("x") || t.getName().equals("y")){
				throw new ParseException("Variable cannot be x or y");
			}
			IdentifierToken token = (IdentifierToken) tokens.pop();
			Variable variable = new Variable(token.getName());
			return new Assignment(variable, rhsExpression);
		}		
		return null;	// Throw an error here if not an identifier token
	}

}
