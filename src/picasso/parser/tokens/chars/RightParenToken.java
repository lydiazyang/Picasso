package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a right parenthesis in the Picasso programming language
 */
public class RightParenToken extends CharToken {
	
	private static final int ORDER = 1;

	public RightParenToken() {
		super(CharConstants.RIGHT_PAREN);
	}
	
}
