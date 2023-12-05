package picasso;

import java.awt.Dimension;
import picasso.parser.tokens.*;
import picasso.parser.tokens.operations.MultiplyToken;
import picasso.parser.tokens.operations.OperationInterface;
import picasso.view.Frame;

/**
 * Starting point for Picasso.
 * 
 * @author Robert Duvall (rcd@cs.duke.edu)
 */
public class Main {
	public static final Dimension SIZE = new Dimension(600, 600);

	public static void main(String[] args) {
		Frame frame = new Frame(SIZE);
		frame.setVisible(true);
		Token multiplyToken = new MultiplyToken();
		System.out.println(multiplyToken instanceof OperationInterface);  // Should print true
		System.out.println(multiplyToken.getClass().getName());       // Should print MultiplyToken
		System.out.println(multiplyToken);

	}
}
