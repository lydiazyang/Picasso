package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.operations.AdditionToken;

/**
 * Represents the Plus operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Addition extends BinaryOperator {

	/**
	 * Creates a wrap expression that takes as a parameter the given expression
	 * 
	 * @param param the expressions to add
	 */
    public Addition(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right);
        token = new AdditionToken();
    }

	/**
	 * Returns the string representation of the function in the format "left+right"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (left.toString() + token.toString() + right.toString());
	}

    /**
	 * Evaluates this expression at the given x,y point by evaluating the addition of x and y 
	 * 
	 * @return the color from evaluating the addition of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        RGBColor leftResult = left.evaluate(x, y);
        RGBColor rightResult = right.evaluate(x, y);

        // Perform addition operation
        double red = leftResult.getRed() + rightResult.getRed();
        double green = leftResult.getGreen() + rightResult.getGreen();
        double blue = leftResult.getBlue() + rightResult.getBlue();

        return new RGBColor(red, green, blue);
    }
    
}
