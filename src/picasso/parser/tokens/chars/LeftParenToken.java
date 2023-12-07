package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a left parenthesis in the Picasso programming language
 * 
 */
public class LeftParenToken extends CharToken {
	
	private static final int ORDER = 1;
	
	public LeftParenToken() {
		super(CharConstants.LEFT_PAREN);
	}
	
	public int getOrder() {
		return ORDER;
	}
}
