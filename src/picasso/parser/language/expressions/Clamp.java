package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the clamp function in the Picasso language.
 * 
 * @author Han Huynh
 * 
 */
public class Clamp extends UnaryFunction {
	/**
	 * Create a clamp expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to clamp
	 */
	public Clamp(ExpressionTreeNode param) {
		super(param);
	}
	
	/**
	 * Evaluates this expression at the given x,y point by evaluating the clamp of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the clamp of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double min = -1;
		double max = 1;
		double red = Math.max(min, Math.min(max, result.getRed()));
		double green = Math.max(min, Math.min(max, result.getGreen()));
		double blue = Math.max(min, Math.min(max, result.getBlue()));

		return new RGBColor(red, green, blue);
	}
}
