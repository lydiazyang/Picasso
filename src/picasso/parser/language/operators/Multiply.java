package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.MultiplyToken;

public class Multiply extends BinaryOperator{


		/**
		 * Creates a multiply expression that takes as a parameter the given expression
		 * 
		 * @param param the expressions to multiply
		 */
	    public Multiply(ExpressionTreeNode left, ExpressionTreeNode right) {
	        super(left, right, new MultiplyToken());
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

	        // Perform multiplication operation
	        double red = leftResult.getRed() * rightResult.getRed();
	        double green = leftResult.getGreen() * rightResult.getGreen();
	        double blue = leftResult.getBlue() * rightResult.getBlue();

	        return new RGBColor(red, green, blue);
	    }
}