package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.PlusToken;

/**
 * Represents addition in a Picasso expression
 * 
 * @author Jenna Bernstein
 * 
 */
public class Plus extends BinaryOperator {

	/**
	 * Creates a wrap expression that takes as a parameter the given expression
	 * 
	 * @param param the expressions to add
	 */
    public Plus(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right, new PlusToken());
    }


	/**
	 * Evaluates this expression at the given x,y point by evaluating the addition of
	 * the function's parameters.
	 * 
	 * @return the color from evaluating the additions of the expression's parameters
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
    
	/**
	 * Returns "+", the representation of this variable in Picasso expressions
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "+";
	}
}
