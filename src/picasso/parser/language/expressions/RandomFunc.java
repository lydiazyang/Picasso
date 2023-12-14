package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
import java.util.Random;

/**
 * Represents the random function in the Picasso language which takes no parameters and evaluates to 1 random color.
 * 
 * @author Jenna Bernstein
 * 
 */
public class RandomFunc extends ExpressionTreeNode {
	private RGBColor color;
	/**
	 * Create a random expression that takes as a parameter the given expression
	 * 
	 */
	public RandomFunc() {
		Random random = new Random();
		double scale = Math.pow(10, 3);
        Double red = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;
        Double green = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;
        Double blue = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;
		this.color = new RGBColor(red, green, blue);
	}

	/**
	 * Returns the color, which is the same for every x,y value and does not need to be re-evaluated each time. 
	 * 
	 * @return the random color for this function
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		return color;
	}

}
