package picasso.parser.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;

public abstract class BinaryOperator extends ExpressionTreeNode{

	ExpressionTreeNode left;
	ExpressionTreeNode right;

	/**
	 * 
	 * @param left
	 * @param right
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
		String classname = this.getClass().getName();
		return  "(" + left + classname.substring(classname.lastIndexOf(".") + 1).toString() + right + ")";
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

}
