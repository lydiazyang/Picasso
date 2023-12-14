package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
/**
 * Represents the yCrCbToRBG function in the Picasso language.
 * 
 * @author Lydia Yang
 */
public class YCrCbToRGB extends UnaryFunction {
	/**
	 * Create an yCrCbToRGB expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to abs
	 */
	public YCrCbToRGB(ExpressionTreeNode param) {
		super(param);
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the absolute value of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the absolute value of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor c = param.evaluate(x, y);
		double red = c.getRed() + c.getBlue() * 1.4022;
        double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
        double blue = c.getRed() + c.getGreen() * 1.7710;
        return new RGBColor(red, green, blue);
	}


}
