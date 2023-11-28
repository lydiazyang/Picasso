package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the wrap function in the Picasso language.
 * 
 */
public class Wrap extends UnaryFunction {

	/**
	 * Create a wrap expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to wrap
	 */
	public Wrap(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * 
	 * @return the color from evaluating the wrapped value of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = wrapValue(result.getRed());
		double green = wrapValue(result.getGreen());
		double blue = wrapValue(result.getBlue());

		return new RGBColor(red, green, blue);
	}
	
	private double wrapValue(double value) {
		// Wrapping helper function
		double wrappedValue = Math.floor(value);
		// Wrap negative values
		while (wrappedValue < -1) {
			wrappedValue += 2;
		}
		// Wrap positive values
		while (wrappedValue > 1) {
			wrappedValue -= 2;
		}
		return wrappedValue;
	}

}
