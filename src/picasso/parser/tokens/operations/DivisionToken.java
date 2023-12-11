package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the division token
 * @author Jenna Bernstein
 */
public class DivisionToken extends CharToken implements OperationInterface{
	private static int order = Token.MULTIPLICATION_OR_DIVISION;

	public DivisionToken() {
		super(CharConstants.SLASH);
	}
	
	@Override
	public int getOrder() {
		return order;
	}

}
