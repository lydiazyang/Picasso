package picasso.parser.tokens.chars;

import picasso.parser.language.CharConstants;

/**
 * Represents a right bracket in the Picasso programming language
 *
 */
public class RightBracketToken extends CharToken {
	
	private static final int ORDER = 1;

	public RightBracketToken() {
		super(CharConstants.RIGHT_BRACKET);
	}

}
