
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.tokens.*;

/**
 * Represents the multiplication sign token
 * 
 */
public class MultiplyToken extends CharToken implements OperationInterface {
	private static int order = Token.MULTIPLICATION_OR_DIVISION;
	
	
	public MultiplyToken() {
		super(CharConstants.STAR);
	}

	
	@Override
	public int getOrder() {
		return order;
	}

}
