/**
 * 
 */
package picasso.parser.tokens;

/**
 * Represents tokens in the Picasso programming language
 * 
 * @author Robert C. Duvall
 * @author Sara Sprenkle
 *
 */
public abstract class Token {
	
	protected static final int CONSTANT = 0;
	protected static final int GROUPING = 1;
	protected static final int MULTIPLICATION_OR_DIVISION = 2;
	protected static final int ADDITION_OR_SUBTRACTION = 3;
	

	private String description;

	/**
	 * Creates a token with the given description
	 * 
	 * @param description the token's description
	 */
	public Token(String description) {
		this.description = description;
	}

	/**
	 * Represents the token by its description
	 * 
	 * @return the token's description
	 */
	@Override
	public String toString() {
		return description;
	}

	/**
	 * Returns true if this token represents a constant, false otherwise
	 * 
	 * @return true iff this Token represents a constant
	 */
	public abstract boolean isConstant();

	/**
	 * Returns true if this token represents a function, false otherwise
	 * 
	 * @return true iff this Token represents a function
	 */
	public abstract boolean isFunction();

	/**
	 * Returns the order of operations number for a specific token
	 * 
	 * @return order of operations integer for the token
	 */
	public abstract int getOrder();
	

}
