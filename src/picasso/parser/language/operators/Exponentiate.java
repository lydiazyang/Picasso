package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.operations.ExponentiateToken;
import picasso.parser.tokens.operations.MultiplyToken;
/**
 * Represents the Multiplication operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Exponentiate extends BinaryOperator{

		/**
		 * Creates a multiply expression that takes as a parameter the given expression
		 * 
		 * @param param the expressions to multiply
		 */
	    public Exponentiate(ExpressionTreeNode left, ExpressionTreeNode right) {
	        super(left, right);
	        token = new ExponentiateToken();
	    }

		/**
		 * Returns the string representation of the function in the format "left*right"
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return  (left.toString() + token.toString() + right.toString());
		}

	    /**
		 * Evaluates this expression at the given x,y point by evaluating the multiplication of x and y 
		 * 
		 * @return the color from evaluating the multiplication of x and y
		 */
		@Override
	    public RGBColor evaluate(double x, double y) {
	        RGBColor leftResult = left.evaluate(x, y);
	        RGBColor rightResult = right.evaluate(x, y);

	        // Perform exponent operation
	        double red = Math.pow(leftResult.getRed(), rightResult.getRed());
	        double green = Math.pow(leftResult.getGreen(), rightResult.getGreen());
	        double blue = Math.pow(leftResult.getBlue(), rightResult.getBlue());
	        
	        return new RGBColor(red, green, blue);
	    }
}
