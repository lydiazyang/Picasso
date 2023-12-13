package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

/**
 * Evaluate an expression for each pixel in a image.
 * 
 * @author Robert C Duvall
 * @author Sara Sprenkle
 */
@SuppressWarnings("deprecation")
public class Evaluator implements Command<Pixmap> {
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	private JTextField input;
	// Stores the current expression being evaluated
	private List<String> expressionList;
	private PropertyChangeSupport propertyChangeSupport;
	
	public Evaluator(JTextField input) {
		this.input = input;
		this.expressionList = new ArrayList<>();
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		expressionList.add(input.getText());
	}

	/**
	 * Evaluate an expression for each point in the image.
	 */
	public void execute(Pixmap target) {
		try {
		// create the expression to evaluate just once
			// evaluate it for each pixel
			if (!input.getText().startsWith("//"))  {
				ExpressionTreeNode expr = createExpression(input.getText());
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
			expressionList.add(input.getText());
			propertyChangeSupport.firePropertyChange("expressionList", null, expressionList); 
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "The expression you entered is currently unsupported. Please enter a new expression.", "Parse Exception Error",0, null);}
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
	ExpressionTreeNode createExpression(String function) {
		// Note, when you're testing, you can use the ExpressionTreeGenerator to
		// generate expression trees from strings, or you can create expression
		// objects directly (as in the commented statement below).

		//String test = "x + y";

		ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
		return expTreeGen.makeExpression(function);
		// return new Multiply( new X(), new Y() );
	}
	
	public List<String> getExpressionList() {
		return expressionList;
	}
	
	// Methods for managing observers

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    public void setInputText(String text) {
    	input.setText(text);
    	execute(new Pixmap());
    	propertyChangeSupport.firePropertyChange("inputText", null, input.getText());
    }


}
