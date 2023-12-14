package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;

/**
 * Represents a function that takes 2 arguments.
 * 
 * @author Jenna Bernstein
 *
 */
public abstract class UnaryOperator extends ExpressionTreeNode{

	ExpressionTreeNode exp;
	Token token;

	/**
	 * 
	 * @param left
	 * @param right
	 */
	public UnaryOperator(ExpressionTreeNode exp) {
		this.exp = exp;
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter> <token> <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  token + "(" + exp + ")";
	}
	
	/**
	 * Returns true if the binary operator expressions are the same, are both binary operators, have the same class type, and have the same variables and token
	 * 
	 * @param Object o
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof BinaryOperator)) {
			return false;
		}
 
		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		UnaryOperator uo = (UnaryOperator) o;

		// check if their parameters are equal
		if (!(this.exp.equals(uo.exp)) && (!this.token.equals(uo.token))) {
			return false;
		}
		return true;
	}

}
