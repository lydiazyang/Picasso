package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents a function that takes two arguments.
 * 
 * @author Han Huynh
 */
public abstract class MultiArgumentFunction extends ExpressionTreeNode {

	ExpressionTreeNode xParam;
	ExpressionTreeNode yParam;

	/**
	 * 
	 * @param param
	 */
	public MultiArgumentFunction(ExpressionTreeNode xParam, ExpressionTreeNode yParam) {
		this.xParam = xParam;
		this.yParam = yParam;
	}

	/**
	 * Returns the string representation of the function in the format "<ClassName>:
	 * <parameter>"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String classname = this.getClass().getName();
		return classname.substring(classname.lastIndexOf(".") + 1) + "(" + xParam + ")" + "(" + yParam + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof UnaryFunction)) {
			return false;
		}

		// Make sure the objects are the same type

		if (o.getClass() != this.getClass()) {
			return false;
		}

		MultiArgumentFunction maf = (MultiArgumentFunction) o;

		// check if their parameters are equal
		if (!this.xParam.equals(maf.xParam)) {
			return false;
		}
		
		if (!this.yParam.equals(maf.yParam)) {
			return false;
		}
		return true;
	}

}
