package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the Assignment token
 * 
 */
public class AssignmentToken extends CharToken implements OperationInterface {
	private static int order = Token.EQUALS;
	
	public AssignmentToken() {
		super(CharConstants.EQUAL);
	}
	@Override
	public int getOrder() {
		return order;
	}
}
