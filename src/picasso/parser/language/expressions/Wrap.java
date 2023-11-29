package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the wrap function in the Picasso language.
 * 
 */
public class Wrap extends UnaryFunction {

	/**
	 * Creates a wrap expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to wrap
	 */
	public Wrap(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates the expression at the given x, y point by wrapping the value at that 
	 * point around 0 (If the number exceeds 1 it wraps around 0, not -1 and vice versa)
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
	/**
	 * Puts value through a while loop to subtract/add 1 until the value falls between
	 * the correct maximum and minimum values
	 * 
	 * @param value the value to wrap
	 * @return the wrapped value
	 */
	private double wrapValue(double value) {
		// Set maximum and minimum integers
		int max = 1;
		int min = -1;		
		// Wrapping helper function
		double wrappedValue = Math.floor(value);
		// Wrap values lower than minimum
		while (wrappedValue < min) {
			wrappedValue += 1;
		}
		// Wrap values higher than maximum
		while (wrappedValue > max) {
			wrappedValue -= 1;
		}
		return wrappedValue;
	}

}
