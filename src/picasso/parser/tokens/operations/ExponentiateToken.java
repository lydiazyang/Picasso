
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.tokens.*;

/**
 * Represents the exponentiate sign token
 * @author Jenna Bernstein
 */
public class ExponentiateToken extends CharToken implements OperationInterface {
	private static int order = Token.EXPONENT;
	
	public ExponentiateToken() {
		super(CharConstants.CARET);
	}
	
	@Override
	public int getOrder() {
		return order;
	}

}
