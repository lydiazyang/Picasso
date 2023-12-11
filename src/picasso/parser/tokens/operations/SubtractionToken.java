package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the subtraction sign token
 * @author Jenna Bernstein
 */
public class SubtractionToken  extends CharToken implements OperationInterface{
	private static int order = Token.ADDITION_OR_SUBTRACTION;

	public SubtractionToken() {
		super(CharConstants.MINUS);
	}

	@Override
	public int getOrder() {
		return order;
	}
}
