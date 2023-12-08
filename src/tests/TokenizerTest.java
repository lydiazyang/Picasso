package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.ParseException;
import picasso.parser.Tokenizer;
import picasso.parser.language.operators.Addition;
import picasso.parser.tokens.*;
import picasso.parser.tokens.chars.*;
import picasso.parser.tokens.functions.*;
import picasso.parser.tokens.operations.AdditionToken;
import picasso.parser.tokens.operations.AssignmentToken;
import picasso.parser.tokens.operations.MultiplyToken;

/**
 * Tests that the tokenizer tokens as expected. 
 * @author Sara Sprenkle
 */
public class TokenizerTest {

	Tokenizer tokenizer;
	List<Token> tokens;

	@BeforeEach
	public void setUp() throws Exception {
		tokenizer = new Tokenizer();
	}
	
	/**
	 * Test that parsing an expression with a comment works
	 */
	@Test
	public void testTokenizeComment() {
		String expression = "x // this is a comment";
		List<Token> tokens = tokenizer.parseTokens(expression);
		assertEquals(new IdentifierToken("x"), tokens.get(0));
		assertEquals(1, tokens.size());
		
		expression = "// everything is a comment";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(0, tokens.size());
	}

	/**
	 * Test that parsing a constant works
	 */
	@Test
	public void testTokenizeConstant() {
		String expression = ".324";
		List<Token> tokens = tokenizer.parseTokens(expression);
		assertEquals(new NumberToken(.324), tokens.get(0));

		expression = "-1";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new NumberToken(-1), tokens.get(0));

		// No problems here; problem will be in next step (Semantic Analysis)
		expression = "-1.2";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new NumberToken(-1.2), tokens.get(0));
	}

	@Test
	public void testTokenizeColor() {
		String expression = "[1, 1, 1]";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new ColorToken(1, 1, 1), tokens.get(0));

		expression = "[-1, 0, .5]";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new ColorToken(-1, 0, .5), tokens.get(0));
	}

	@Test
	public void testTokenizeInvalidColor() {
		String expression = "[1, 1.0001, 1]";

		assertThrows(ParseException.class, () -> {
			tokens = tokenizer.parseTokens(expression);
		});
	}

	@Test
	public void testTokenizeBasicFunctionExpression() {
		String expression = "floor(x)";
		tokens = tokenizer.parseTokens(expression);
		assertEquals(new FloorToken(), tokens.get(0));
		assertEquals(new LeftParenToken(), tokens.get(1));
		assertEquals(new IdentifierToken("x"), tokens.get(2));
		assertEquals(new RightParenToken(), tokens.get(3));
	}

	@Test
	public void testTokenizeCombinedFunctionExpression() {
		String expression = "ceil(floor(x) + y)";
		List<Token> tokens = tokenizer.parseTokens(expression);
		System.out.println(tokens);
		assertEquals(new CeilToken(), tokens.get(0));
		assertEquals(new LeftParenToken(), tokens.get(1));
		assertEquals(new FloorToken(), tokens.get(2));
		assertEquals(new LeftParenToken(), tokens.get(3));
		assertEquals(new IdentifierToken("x"), tokens.get(4));
		assertEquals(new RightParenToken(), tokens.get(5));
		assertEquals(new AdditionToken(), tokens.get(6));
		assertEquals(new IdentifierToken("y"), tokens.get(7));
		assertEquals(new RightParenToken(), tokens.get(8));
		

		//expression = "sin(perlinColor(x, y))";
		//tokens = tokenizer.parseTokens(expression);
		//System.out.println(tokens);
	}

	@Test
	public void testTokenizeCombinedArithmeticExpression() {
		String expression = "x*y+y+y*x";
		List<Token> tokens = tokenizer.parseTokens(expression);
		System.out.println(tokens);
		assertEquals(new IdentifierToken("x"), tokens.get(0));
		assertEquals(new MultiplyToken(), tokens.get(1));
		assertEquals(new IdentifierToken("y"), tokens.get(2));
		assertEquals(new AdditionToken(), tokens.get(3));
		assertEquals(new IdentifierToken("y"), tokens.get(4));
		assertEquals(new AdditionToken(), tokens.get(5));
		assertEquals(new IdentifierToken("y"), tokens.get(6));
		assertEquals(new MultiplyToken(), tokens.get(7));
		assertEquals(new IdentifierToken("x"), tokens.get(8));
		
	}

	@Test
	public void testTokenizeAssignment() {
	    String expression = "a = [1, 0, 0]";
	    List<Token> tokens = tokenizer.parseTokens(expression);
	    assertEquals(new IdentifierToken("a"), tokens.get(0));
	    assertEquals(new AssignmentToken(), tokens.get(1));
	    assertEquals(new ColorToken(1, 0, 0), tokens.get(2));
	    assertEquals(3, tokens.size());
	}


}
