
package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

/**
 * Represents the multiplication sign token
 * 
 */
public class MultiplyToken extends CharToken implements OperationInterface {
	public MultiplyToken() {
		super(CharConstants.STAR);
	}
}
