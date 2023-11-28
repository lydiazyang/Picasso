package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;

import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

public class Input implements Command<Pixmap> {
	
	private String inputFunction;

	public Input() {
		this.inputFunction = inputFunction;
	}

	/**
	 * Evaluate an expression for each point in the image.
	 */
	@Override
	public void execute(Pixmap target) {
		...
	}

}
