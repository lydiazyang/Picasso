/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.model.Pixmap;
import picasso.parser.SemanticAnalyzer;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.tokens.*;
import picasso.parser.tokens.functions.ImageWrapToken;
import picasso.parser.tokens.operations.*;
import picasso.parser.language.operators.*;

/**
 * Test the parsing from the Stack (not as easy as using a String as input, but
 * helps to isolate where the problem is)
 * 
 * @author Sara Sprenkle
 *
 */
class SemanticAnalyzerTest {

	private SemanticAnalyzer semAnalyzer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		semAnalyzer = SemanticAnalyzer.getInstance();
	}

	@Test
	void testParseAddition() {

		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new AdditionToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		Addition plus = new Addition(new X(), new Y());

		assertEquals(new Addition(new X(), new Y()), actual);
		assertEquals(plus, actual);
	}
	
	@Test
	void testParseMultiplication() {
		Stack<Token> tokens = new Stack<>();
		tokens.push(new IdentifierToken("x"));
		tokens.push(new IdentifierToken("y"));
		tokens.push(new MultiplyToken());

		ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
		Multiply times = new Multiply(new X(), new Y());

		assertEquals(new Multiply(new X(), new Y()), actual);
		assertEquals(times, actual);
	}
	
    @Test
    void testParseAssignment() {
        // Create a test case for Assignment
        Stack<Token> tokens = new Stack<>();
        tokens.push(new IdentifierToken("a"));
        tokens.push(new ColorToken(1, 0, 0)); 
        tokens.push(new AssignmentToken());

        ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
        Assignment assignment = new Assignment(new Variable("a"), new RGBColor(1, 0, 0));

        assertEquals(new RGBColor(1, 0, 0), actual.evaluate(0, 0)); 
        assertEquals(assignment, actual);
    }	
    
    @Test
    void testParseImageWrap() {
        // Create a test case for Image Wrap
        Stack<Token> tokens = new Stack<>();
        tokens.push(new ImageToken("vortex.jpg"));
        tokens.push(new IdentifierToken("x"));
        tokens.push(new IdentifierToken("x"));
        tokens.push(new AdditionToken());
        tokens.push(new IdentifierToken("y"));
        tokens.push(new ImageWrapToken());
 
        ExpressionTreeNode actual = semAnalyzer.generateExpressionTree(tokens);
        Pixmap image = new Pixmap(Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + "vortex.jpg");
        ImageWrap iw = new ImageWrap(image, new Addition(new X(), new X()), new Y());

        assertEquals(iw.evaluate(0, 0), actual.evaluate(0, 0)); 
        assertEquals(iw, actual);
    }	

}
