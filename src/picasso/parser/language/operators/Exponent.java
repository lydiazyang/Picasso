package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
/**
 * Represents the Exponent operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Exponent extends BinaryOperator {

	/**
	 * Creates a exponential expression that takes as a parameter the given left and right of the expression
	 * 
	 * @param param the expressions to exponentiate
	 */
    public Exponent(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right);
    }


    /**
	 * Evaluates this expression at the given x,y point by evaluating the exponent
	 * 
	 * @return the color from evaluating the exponent
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        RGBColor leftResult = left.evaluate(x, y);
        RGBColor rightResult = right.evaluate(x, y);

        // Perform exponent operation
        double red = Math.pow(leftResult.getRed(),  rightResult.getRed());
        double green = Math.pow(leftResult.getGreen(), rightResult.getGreen());
        double blue = Math.pow(leftResult.getBlue(), rightResult.getBlue());

        return new RGBColor(red, green, blue);
    }
    
}

