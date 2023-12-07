package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.tokens.*;

/**
 * Represents the plus sign token
 * 
 */
public class AdditionToken extends CharToken implements OperationInterface {
	private static final int order = Token.ADDITION_OR_SUBTRACTION;

	public AdditionToken() {
		super(CharConstants.PLUS);
	}
	
	
	@Override
	public int getOrder() {
		return order;
	}
	

}
