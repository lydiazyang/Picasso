
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;
import picasso.parser.tokens.*;

/**
 * Represents the multiplication sign token
 * 
 */
public class ModToken extends CharToken implements OperationInterface {
	private static int order = Token.MULTIPLICATION_OR_DIVISION;
	
	
	public ModToken() {
		super(CharConstants.MOD);
	}

	
	@Override
	public int getOrder() {
		return order;
	}

}
