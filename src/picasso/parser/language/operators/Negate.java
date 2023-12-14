package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.UnaryFunction;
/**
 * Represents the negate function in the Picasso language.
 * 
 * @author Jenna Bernstein
 */
public class Negate extends UnaryOperator {
	/**
	 * Create an tan expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to tan
	 */
	public Negate(ExpressionTreeNode param) {
		super(param);
	}
	
	/**
     * Evaluates this expression at the given x, y point by performing color inversion.
     *
     * @return the color from performing color inversion on the expression's parameter
     */
    @Override
    public RGBColor evaluate(double x, double y) {
        RGBColor result = exp.evaluate(x, y);
        double red = 0.0 - result.getRed();
        double green = 0.0 - result.getGreen();
        double blue = 0.0 - result.getBlue();

        return new RGBColor(red, green, blue);
    }


}