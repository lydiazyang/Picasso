package picasso.parser.tokens.operations;

import picasso.parser.language.CharConstants;
import picasso.parser.tokens.chars.CharToken;

public class NegateToken extends CharToken implements OperationInterface{

	public NegateToken() {
		super(CharConstants.BANG);
	}

	
}
