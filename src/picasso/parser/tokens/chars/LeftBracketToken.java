/**
 * 
 */
package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a left bracket in the Picasso programming language
 *
 */
public class LeftBracketToken extends CharToken {
	
	private static final int ORDER = 1;

	public LeftBracketToken() {
		super(CharConstants.LEFT_BRACKET);
	}
	
	public int getOrder() {
		return ORDER;
	}

}
