package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.PlusToken;

/**
 * Represents the Plus operation in the Picasso language.
 * 
 * @author Jenna Bernstein
 * 
 */
public class Plus extends BinaryOperator {

	/**
	 * Creates a wrap expression that takes as a parameter the given expression
	 * 
	 * @param param the expressions to add
	 */
    public Plus(ExpressionTreeNode left, ExpressionTreeNode right) {
        super(left, right, new PlusToken());
    }


	/**
	 * Create a plus expression that takes as a parameter the given expression tree nodes that are added and plus token
	 * 
	 * @param ExpressionTreeNode left
	 * @param ExpressionTreeNode right
	 * @param Token token
	 */
    public Plus(ExpressionTreeNode left, ExpressionTreeNode right, Token token) {
        super(left, right, token);
    }

    /**
	 * Evaluates this expression at the given x,y point by evaluating the addition of x and y 
	 * 
	 * @return the color from evaluating the addition of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        RGBColor leftResult = left.evaluate(x, y);
        RGBColor rightResult = right.evaluate(x, y);

        // Perform addition operation
        double red = leftResult.getRed() + rightResult.getRed();
        double green = leftResult.getGreen() + rightResult.getGreen();
        double blue = leftResult.getBlue() + rightResult.getBlue();

        return new RGBColor(red, green, blue);
    }
    
}
