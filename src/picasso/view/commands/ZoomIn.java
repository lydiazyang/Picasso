package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

/**
 * Zooms in on the current displayed image.
 * 
 * @author Han Huynh
 */
public class ZoomIn implements Command<Pixmap> {
	private Evaluator evaluator;
	private StringEvaluator sEvaluator;
	public ZoomIn(Evaluator evaluator, StringEvaluator sEvaluator) {
		this.evaluator = evaluator;
		this. sEvaluator = sEvaluator;
	}

	/**
	 * Zooms in 10% of current image
	 */
	public void execute(Pixmap target) {	
		evaluator.scaleDown();
		String input;
		System.out.print(evaluator.isString());
		if (evaluator.isString()) {
				input = sEvaluator.generateExpressionFromString(evaluator.getInput());
				System.out.print(input);
		}else {
			input = evaluator.getInput();
		}

		try {
			// create the expression to evaluate just once
				// evaluate it for each pixel
				if (!input.startsWith("//"))  {
					ExpressionTreeNode expr = createExpression(input);
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
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "The expression you entered is currently unsupported. Please enter a new expression.", "Parse Exception Error",0, null);
			}
		}
	
	
	/**
	 * Convert from image space to domain space.
	 */
	protected double imageToDomainScale(int value, int bounds) {
		double scaledMin = evaluator.getScaledMin();
		double scaledMax = evaluator.getScaledMax();
		double range = scaledMax - scaledMin;
		return ((double) value / bounds) * range + scaledMin;
	}

	/**
	 * 
	 * A place holder for a more interesting way to build the expression.
	 */
	ExpressionTreeNode createExpression(String function) {
		// Note, when you're testing, you can use the ExpressionTreeGenerator to
		// generate expression trees from strings, or you can create expression
		// objects directly (as in the commented statement below).

		//String test = "x + y";

		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		return expTreeGen.makeExpression(function);
		// return new Multiply( new X(), new Y() );
	}


}
