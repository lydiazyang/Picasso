package picasso.parser;

import java.util.Stack;


import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.YCrCbToRGB;
import picasso.parser.tokens.Token;

/**
 * Handles parsing the YCrCb To RGB function.
 * 
 * @author Lydia Yang
 */
public class YCrCbToRGBAnalyzer extends UnaryFunctionAnalyzer {

	@Override
	public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop(); // Need to remove the abs token
		// the parameter is the next token(s) on the stack.
		// But, it needs to be processed
		ExpressionTreeNode paramETN = SemanticAnalyzer.getInstance().generateExpressionTree(tokens);

		return new YCrCbToRGB(paramETN);
	}

}
