package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
/**
 * Represents the rgbToYCrCb function in the Picasso language.
 * 
 * @author Lydia Yang
 */
public class RgbToYCrCb extends UnaryFunction {
	/**
	 * Create an yCrCbToRGB expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to abs
	 */
	public RgbToYCrCb(ExpressionTreeNode param) {
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
		double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
        double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
        double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
        return new RGBColor(red, green, blue);
	}


}
