package picasso.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.X;
import picasso.parser.language.expressions.Y;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;

/**
 * Handle an identifier token 
 * 
 * @author Sara Sprenkle
 *
 */
public class IdentifierAnalyzer implements SemanticAnalyzerInterface {

	static Map<String, ExpressionTreeNode> idToExpression = new HashMap<String, ExpressionTreeNode>();
	static {
		// We always have x and y defined.
		idToExpression.put("x", new X());
		idToExpression.put("y", new Y());
	}

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		IdentifierToken t = (IdentifierToken) tokens.pop();
		String id = t.getName();
		ExpressionTreeNode mapped = idToExpression.get(id);
		if (mapped != null) {
			return mapped;
		}
		else {
			throw new ParseException("Extra operands without operators or functions");
		}

		// Throws exception if operands/functions that are not supported
		// TODO : What should we do if we don't recognize the identifier?
		// Is that an error? Or, could there a valid reason?
	}
	
	/**
	 * Store the assignment result in the symbol table
	 * 
	 * @param variable the variable name
	 * @param result the RGBColor result
	 */
	public static void storeAssignmentResult(String variable, ExpressionTreeNode result) {
		idToExpression.put(variable, result);
		// System.out.println("Map:"+idToExpression);
	}

}
