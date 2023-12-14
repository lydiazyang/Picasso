package picasso.parser;

import java.util.Stack;
import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageClip;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.ImageToken;

/**
 * Handles parsing the image clip function.
 * 
 * @author Han Huynh
 * 
 */
public  class ImageClipAnalyzer  implements SemanticAnalyzerInterface {

	@Override
	public  ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop();  // Remove the image clip token
		
		// get expression to calculate y coords
		ExpressionTreeNode yParam = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// get expression to calculate x coords
		ExpressionTreeNode xParam = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// pop off filename
		ImageToken file = (ImageToken) tokens.pop();
		
		// get fileName and use to create Pixmap object
		Pixmap image = new Pixmap(file.getFilePath());
		
		return new ImageClip(image, xParam, yParam);
	}
	

}
