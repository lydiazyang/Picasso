package picasso.parser.tokens.operations;

/**
 * A marker interface
 * 
 */
public interface OperationInterface {
	static final int MULTIPLY_OR_DIVIDE = 3;
	static final int ADDITION_OR_SUBTRACTION = 2;
	
	int getOrder();
}
