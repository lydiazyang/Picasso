package picasso.parser.operators;

import java.util.Objects;


import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;

public class Plus extends BinaryOperator {
	

    public Plus(ExpressionTreeNode left, ExpressionTreeNode right, Token token) {
        super(left, right, token);
    }

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
    
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plus plus = (Plus) o;
        if (Objects.equals(left, plus.left) && Objects.equals(right, plus.right) || (Objects.equals(left, plus.right) && Objects.equals(right, plus.left))){
        	return true;
        }
        else {
        		return false;
        }
    }


}
