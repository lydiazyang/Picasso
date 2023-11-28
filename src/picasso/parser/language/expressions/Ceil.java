package picasso.parser.language.expressions;

import picasso.parser.language.ExpressionTreeNode;

public class Ceil extends UnaryFunction{

	public Ceil(ExpressionTreeNode param){
		super(param);
	}

	@Override
	public RGBColor evaluate(double x, double y) {
		RGBColor result = param.evaluate(x, y);
		double red = Math.ceil(result.getRed());
		double green = Math.ceil(result.getGreen());
		double blue = Math.ceil(result.getBlue());

		return new RGBColor(red, green, blue);
	}

}
