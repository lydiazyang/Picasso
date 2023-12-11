package picasso.parser.tokens.functions;

import picasso.parser.tokens.Token;

/**
 * Represents the exponent sign token
 * @author Jenna Bernstein
 */
public class ExpToken extends FunctionToken{
	private static int order = Token.MULTIPLICATION_OR_DIVISION;

	public ExpToken() {
		super("Exponent Function Token");
	}
	
	@Override
	public int getOrder() {
		return order;
	}
}
