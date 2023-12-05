package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the plus sign token
 * 
 */
public class AdditionToken extends CharToken implements OperationInterface {

	public AdditionToken() {
		super(CharConstants.PLUS);
	}
	
	
	public int getOrder() {
		return ADDITION_OR_SUBTRACTION;
	}

}
