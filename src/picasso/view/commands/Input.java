package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;
import picasso.util.ThreadedCommand;

public class Input implements Command<Pixmap> {
	
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	private String inputFunction;

	public Input() {
		this.inputFunction = inputFunction;
	}
	
	public void setFunction(String inputFunction) {
		this.inputFunction = inputFunction;
	}


	/**
	 * Evaluate an expression for each point in the image.
	 */
	@Override
	public void execute(Pixmap target) {
		// create the expression to evaluate just once
		ExpressionTreeNode expr = createExpression(this.inputFunction);
		// evaluate it for each pixel
		Dimension size = target.getSize();
		for (int imageY = 0; imageY < size.height; imageY++) {
			double evalY = imageToDomainScale(imageY, size.height);
			for (int imageX = 0; imageX < size.width; imageX++) {
				double evalX = imageToDomainScale(imageX, size.width);
				Color pixelColor = expr.evaluate(evalX, evalY).toJavaColor();
				target.setColor(imageX, imageY, pixelColor);
			}
		}
	}
	
	/**
	 * Convert from image space to domain space.
	 */
	protected double imageToDomainScale(int value, int bounds) {
		double range = DOMAIN_MAX - DOMAIN_MIN;
		return ((double) value / bounds) * range + DOMAIN_MIN;
	}
	
	/**
	 * 
	 * A place holder for a more interesting way to build the expression.
	 */
	ExpressionTreeNode createExpression(String functionTextField) {
		// Note, when you're testing, you can use the ExpressionTreeGenerator to
		// generate expression trees from strings, or you can create expression
		// objects directly (as in the commented statement below).

		//String test = "floor(y)";
		//String test = "x + y";

		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		return expTreeGen.makeExpression(functionTextField);

		// return new Multiply( new X(), new Y() );
	}

}
