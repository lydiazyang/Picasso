package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.operators.Plus;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.PlusToken;

/**
 * Handles parsing the plus or "addition function".
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 * 
 */
public class PlusAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the plus token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		ExpressionTreeNode param1 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		ExpressionTreeNode param2 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Plus(param1, param2, new PlusToken());
	}

}
