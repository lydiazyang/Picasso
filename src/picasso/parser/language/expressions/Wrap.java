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
	 * point around the range -1, 1
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
	 * Puts value through a while loop to subtract/add 2 until the value falls between
	 * the correct maximum and minimum values
	 * 
	 * @param value the value to wrap
	 * @return the wrapped value
	 */
	private double wrapValue(double value) {
		// Set maximum and minimum integers
		double max = 1;
		double min = -1;		
		// Instantiate wrapped value
		double wrappedValue = value;
		// Wrap values lower than minimum
		while (wrappedValue < min) {
			wrappedValue += 2;
		}
		// Wrap values higher than maximum
		while (wrappedValue > max) {
			wrappedValue -= 2;
		}
		return wrappedValue;
	}

}
