package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
/**
 * Represents the Subtraction operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Subtraction extends BinaryOperator {

	/**
	 * Creates a subtraction expression that takes as a parameter the given left and right of the expression
	 * 
	 * @param param the expressions to subtract
	 */
    public Subtraction(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right);
    }


    /**
	 * Evaluates this expression at the given x,y point by evaluating the addition of x and y 
	 * 
	 * @return the color from evaluating the subtraction of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        RGBColor leftResult = left.evaluate(x, y);
        RGBColor rightResult = right.evaluate(x, y);

        // Perform subtraction operation
        double red = leftResult.getRed() - rightResult.getRed();
        double green = leftResult.getGreen() - rightResult.getGreen();
        double blue = leftResult.getBlue() - rightResult.getBlue();

        return new RGBColor(red, green, blue);
    }
    
}
