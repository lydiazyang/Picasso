package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the exponent sign token
 * @author Jenna Bernstein
 */
public class ExponentToken extends CharToken implements OperationInterface{
	private static int order = Token.MULTIPLICATION_OR_DIVISION;

	public ExponentToken() {
		super(CharConstants.CARET);
	}
	
	@Override
	public int getOrder() {
		return order;
	}
}
