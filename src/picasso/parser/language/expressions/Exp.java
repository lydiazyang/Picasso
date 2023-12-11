package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.operators.BinaryOperator;
import picasso.parser.tokens.functions.ExpToken;
/**
 * Represents the Exponent operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Exp extends UnaryFunction {

	/**
	 * Create a floor expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to floor
	 */
	public Exp(ExpressionTreeNode param) {
		super(param);
	}

	/**
	 * Evaluates this expression at the given x,y point by evaluating the floor of
	 * the function's parameter.
	 * 
	 * @return the color from evaluating the floor of the expression's parameter
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.exp(result.getRed());
		double green = Math.exp(result.getGreen());
		double blue = Math.exp(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
