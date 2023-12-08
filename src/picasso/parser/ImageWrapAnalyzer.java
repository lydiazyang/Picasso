package picasso.parser;

import java.io.File;
import java.nio.file.Path;
import java.util.Stack;
import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.ImageWrap;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.IdentifierToken;

/**
 * Handles parsing the image wrap function.
 * 
 * @author Han Huynh
 * 
 */
public  class ImageWrapAnalyzer  implements SemanticAnalyzerInterface {

	@Override
	public  ExpressionTreeNode generateExpressionTree(Stack<Token> tokens) {
		tokens.pop();  // Remove the image wrap token
		
		// get expression to calculate y coords
		ExpressionTreeNode yParam = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// get expression to calculate x coords
		ExpressionTreeNode xParam = SemanticAnalyzer.getInstance().generateExpressionTree(
				tokens);
		
		// pop off fileName token
		IdentifierToken file = (IdentifierToken) tokens.pop();
		
		// get fileName and use to create Pixmap object
		String filePath = Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + file.getName();
		Pixmap image = new Pixmap(filePath);
		
		return new ImageWrap(image, xParam, yParam);
	}
	

}
