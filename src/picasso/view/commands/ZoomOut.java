package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JOptionPane;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

/**
 * Zooms out on the current displayed image.
 * 
 * @author Han Huynh
 */
public class ZoomOut implements Command<Pixmap> {
	public static final double DOMAIN_MIN = -1;
	public static final double DOMAIN_MAX = 1;
	private JTextField input;
	
	public ZoomOut(JTextField input) {
		this.input = input;
	}

	/**
	 * Evaluate an expression for each point in the image.
	 */
	public void execute(Pixmap target) {
		
	}

	/**
	 * Convert from image space to domain space.
	 */

	protected double imageToDomainScale(int value, int bounds) {
		double range = DOMAIN_MAX - DOMAIN_MIN;
		return ((double) value / bounds) * range + DOMAIN_MIN;
	}



}
