package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;

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
	public BinaryOperator(ExpressionTreeNode left, ExpressionTreeNode right, Token token) {
		this.left = left;
		this.right = right;
		this.token = token;
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
		if (!((this.left.equals(bo.left)) && (this.right.equals(bo.right))) || (!(this.left.equals(bo.right)) && (this.right.equals(bo.left)))) {
			return false;
		}
		return true;
	}

}
