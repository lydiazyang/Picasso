
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.language.operators.BinaryOperator;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.tokens.operations.OperationInterface;

/**
 * Represents the multiplication sign token
 * 
 */
public class MultiplyToken extends CharToken implements OperationInterface {
	
	
	public MultiplyToken() {
		super(CharConstants.STAR);
	}

	
	@Override
	public int getOrder() {
		return MULTIPLY_OR_DIVIDE;
	}

}
