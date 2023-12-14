package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.Negate;
import picasso.parser.tokens.operations.NegateToken;
import picasso.parser.tokens.Token;
/**
 * Handles parsing the Negate function.
 * 
 * @author Jenna Bernstein
 */
public class NegateAnalyzer implements SemanticAnalyzerInterface {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Remove the negate token
		// the parameters are the next tokens on the stack.
		// But, they need to be processed
		ExpressionTreeNode param1 = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		return new Negate(param1);
	}

}
