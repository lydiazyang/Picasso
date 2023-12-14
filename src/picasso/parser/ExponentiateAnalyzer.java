package picasso.parser;

import java.util.Stack;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.Exponentiate;
import picasso.parser.tokens.Token;

public class ExponentiateAnalyzer implements SemanticAnalyzerInterface {

		@Override
		public ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
			tokens.pop(); // Remove the multiply token
			// the parameters are the next tokens on the stack.
			// But, they need to be processed
			ExpressionTreeNode param1 = SemanticAnalyzer.getInstance().generateExpressionTree(
					tokens);
			ExpressionTreeNode param2 = SemanticAnalyzer.getInstance().generateExpressionTree(
					tokens);
			return new Exponentiate(param2, param1);
	}

}
