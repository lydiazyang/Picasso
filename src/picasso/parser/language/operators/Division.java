package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.operations.DivisionToken;

/**
 * Represents the Division operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Division extends BinaryOperator {

	/**
	 * Creates a division expression that takes as a parameter the given left and right of the expression
	 * 
	 * @param param the expressions to divide
	 */
    public Division(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right);
        token = new DivisionToken();
    }

	/**
	 * Returns the string representation of the function in the format "left/right"
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  (left.toString() + token.toString() + right.toString());
	}

    /**
	 * Evaluates this expression at the given x,y point by evaluating the division of x and y 
	 * 
	 * @return the color from evaluating the division of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        RGBColor leftResult = left.evaluate(x, y);
        RGBColor rightResult = right.evaluate(x, y);

        // Perform division operation
        double red = leftResult.getRed() / rightResult.getRed();
        double green = leftResult.getGreen() / rightResult.getGreen();
        double blue = leftResult.getBlue() / rightResult.getBlue();

        return new RGBColor(red, green, blue);
    }
    
}
