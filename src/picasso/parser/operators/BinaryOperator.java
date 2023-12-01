package picasso.parser.operators;

import java.util.Objects;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;

public abstract class BinaryOperator extends ExpressionTreeNode{

	protected ExpressionTreeNode left;
	protected ExpressionTreeNode right;
	protected Token token;

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
		if ((!this.left.equals(bo.left)) && (!this.right.equals(bo.right))) {
			return false;
		}
		return true;
	}
	

    /**
     * Returns the hash code for this binary operator.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

}
