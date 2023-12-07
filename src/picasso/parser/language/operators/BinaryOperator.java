package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.tokens.Token;

/**
 * Represents a function that takes 2 arguments and an operator.
 * 
 * @author Jenna Bernstein
 *
 */
public abstract class BinaryOperator extends ExpressionTreeNode{

	ExpressionTreeNode left;
	ExpressionTreeNode right;
	Token token;

	/**
	 * 
	 * @param left
	 * @param right
	 * @param token
	 */
	public BinaryOperator(ExpressionTreeNode left, ExpressionTreeNode right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter> <token> <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  "(" + left + token + right + ")";
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

		BinaryOperator bo = (BinaryOperator) o;

		// check if their parameters are equal
		if (!(this.left.equals(bo.right)) && (this.right.equals(bo.left))&& (!this.token.equals(bo.token))) {
			return false;
		}
		return true;
	}

}
