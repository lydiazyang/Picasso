package tests;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Stack;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.parser.language.operators.Addition;
import picasso.parser.language.operators.Assignment;
import picasso.parser.language.operators.Division;
import picasso.parser.language.operators.Exponentiate;
import picasso.parser.language.operators.Mod;
import picasso.parser.language.operators.Subtraction;
import picasso.parser.tokens.ColorToken;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.AdditionToken;
import picasso.parser.tokens.operations.AssignmentToken;
import picasso.parser.tokens.operations.DivisionToken;
import picasso.parser.tokens.operations.ExponentiateToken;
import picasso.parser.tokens.operations.MultiplyToken;

/**
 * Tests of creating an expression tree from a string expression. Will have
 * compiler errors until some code is created.
 * 
 * @author Sara Sprenkle 
 * 
 */
public class ExpressionTreeGeneratorTests {

	private ExpressionTreeGenerator parser;

	@BeforeEach
	public void setUp() throws Exception {
		parser = new ExpressionTreeGenerator();
	}

	@Test
	public void constantExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("[1,-1, 1]");
		assertEquals(new RGBColor(1, -1, 1), e);
	}

	@Test
	public void variableExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x");
		assertEquals(new X(), e);
	}

	@Test
	public void AdditionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x + y");
		assertEquals(new Addition(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x+y");
		assertEquals(new Addition(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] + y");
		assertEquals(new Addition(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x + y + [ -.51, 0, 1]");
		assertEquals(new Addition(new Addition(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void DvisionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x / y");
		assertEquals(new Division(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x/y");
		assertEquals(new Division(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] / y");
		assertEquals(new Division(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x / y / [ -.51, 0, 1]");
		assertEquals(new Division(new Division(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void ModExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x % y");
		assertEquals(new Mod(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x%y");
		assertEquals(new Mod(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] % y");
		assertEquals(new Mod(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x % y % [ -.51, 0, 1]");
		assertEquals(new Mod(new Mod(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void ExponentiateExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x ^ y");
		assertEquals(new Exponentiate(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x^y");
		assertEquals(new Exponentiate(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] ^ y");
		assertEquals(new Exponentiate(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x ^ y ^ [ -.51, 0, 1]");
		assertEquals(new Exponentiate(new Exponentiate(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void SubtractionExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("x - y");
		assertEquals(new Subtraction(new X(), new Y()), e);

		// no spaces!
		e = parser.makeExpression("x-y");
		assertEquals(new Subtraction(new X(), new Y()), e);

		e = parser.makeExpression("[1,.3,-1] - y");
		assertEquals(new Subtraction(new RGBColor(1, .3, -1), new Y()), e);

		e = parser.makeExpression("x - y - [ -.51, 0, 1]");
		assertEquals(new Subtraction(new Addition(new X(), new Y()), new RGBColor(-.51, 0, 1)), e);
	}
	
	@Test
	public void parenthesesExpressionTests() {
		ExpressionTreeNode e = parser.makeExpression("( x + y )");
		assertEquals(new Addition(new X(), new Y()), e);

		e = parser.makeExpression("( x + (y + [ 1, 1, 1] ) )");
		assertEquals(new Addition(new X(), new Addition(new Y(), new RGBColor(1, 1, 1))), e);
	}

	@Test
	public void arithmeticStackTests() {
		Stack<Token> stack1 = parser.infixToPostfix("x + y * x");

		Stack<Token> expected1 = new Stack<>();
		expected1.push(new IdentifierToken("x"));
		expected1.push(new IdentifierToken("y"));
		expected1.push(new IdentifierToken("x"));
		expected1.push(new MultiplyToken());
		expected1.push(new AdditionToken());

		System.out.println(expected1);
		System.out.println(stack1);
		assertEquals(expected1, stack1);
		
		Stack<Token> stack2 = parser.infixToPostfix("x + y * x ^ y");

		Stack<Token> expected2 = new Stack<>();
		expected2.push(new IdentifierToken("x"));
		expected2.push(new IdentifierToken("y"));
		expected2.push(new IdentifierToken("x"));
		expected2.push(new IdentifierToken("y"));
		expected2.push(new ExponentiateToken());
		expected2.push(new MultiplyToken());
		expected2.push(new AdditionToken());

		System.out.println(expected2);
		System.out.println(stack2);
		assertEquals(expected2, stack2);
		
		Stack<Token> stack3 = parser.infixToPostfix("y ^ x / x");

		Stack<Token> expected3 = new Stack<>();
		expected3.push(new IdentifierToken("y"));
		expected3.push(new IdentifierToken("x"));
		expected3.push(new ExponentiateToken());
		expected3.push(new IdentifierToken("x"));
		expected3.push(new DivisionToken());

		System.out.println(expected3);
		System.out.println(stack3);
		assertEquals(expected3, stack3);
		
		Stack<Token> stack4 = parser.infixToPostfix("y + x ^ x");

		Stack<Token> expected4 = new Stack<>();
		expected4.push(new IdentifierToken("y"));
		expected4.push(new IdentifierToken("x"));
		expected4.push(new IdentifierToken("x"));
		expected4.push(new ExponentiateToken());
		expected4.push(new AdditionToken());

		System.out.println(expected4);
		System.out.println(stack4);
		assertEquals(expected4, stack4);
	}

	@Test
	public void arithmeticStackEqualsTests() {
		Stack<Token> stack = parser.infixToPostfix("a=x+y");

		Stack<Token> expected = new Stack<>();
		expected.push(new IdentifierToken("a"));
		expected.push(new IdentifierToken("x"));
		expected.push(new IdentifierToken("y"));
		expected.push(new AdditionToken());
		expected.push(new AssignmentToken());

		assertEquals(expected, stack);
	}

	@Test
	public void floorFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("floor( x )");
		assertEquals(new Floor(new X()), e);

		e = parser.makeExpression("floor( x + y )");
		assertEquals(new Floor(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void cosFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("cos( x )");
		assertEquals(new Cos(new X()), e);

		e = parser.makeExpression("cos( x + y )");
		assertEquals(new Cos(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void tanFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("tan( x )");
		assertEquals(new Tan(new X()), e);

		e = parser.makeExpression("tan( x + y )");
		assertEquals(new Tan(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void atanFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("atan( x )");
		assertEquals(new Atan(new X()), e);

		e = parser.makeExpression("atan( x + y )");
		assertEquals(new Atan(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void expFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("exp( x )");
		assertEquals(new Exp(new X()), e);

		e = parser.makeExpression("exp( x + y )");
		assertEquals(new Exp(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void logFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("log( x )");
		assertEquals(new Log(new X()), e);

		e = parser.makeExpression("log( x + y )");
		assertEquals(new Log(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void perlinBWFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("perlinBW( x, y )");
		assertEquals(new PerlinBW(new X(), new Y()), e);
	}
	
	@Test
	public void perlinColorFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("perlinColor( x, y )");
		assertEquals(new PerlinColor(new X(), new Y()), e);
	}
	
	@Test
	public void rgbToYCrCbFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("rgbToYCrCb( x )");
		assertEquals(new RgbToYCrCb(new X()), e);

		e = parser.makeExpression("rgbToYCrCb( x + y )");
		assertEquals(new RgbToYCrCb(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void yCrCbToRGBFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("yCrCbToRGB( x )");
		assertEquals(new YCrCbToRGB(new X()), e);

		e = parser.makeExpression("yCrCbToRGB( x + y )");
		assertEquals(new YCrCbToRGB(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void ceilFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("ceil( x )");
		assertEquals(new Ceil(new X()), e);
		
		e = parser.makeExpression("ceil( x + y )");
		assertEquals(new Ceil(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void absFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("abs( x )");
		assertEquals(new Abs(new X()), e);
		
		e = parser.makeExpression("abs( x + y )");
		assertEquals(new Abs(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void clampFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("clamp( x )");
		assertEquals(new Clamp(new X()), e);
		
		e = parser.makeExpression("clamp( x + y )");
		assertEquals(new Clamp(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void wrapFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("wrap( x )");
		assertEquals(new Wrap(new X()), e);
		
		e = parser.makeExpression("wrap( x + y )");
		assertEquals(new Wrap(new Addition(new X(), new Y())), e);
	}
	
	@Test
	public void sinFunctionTests() {
		ExpressionTreeNode e = parser.makeExpression("sin( x )");
		assertEquals(new Sin(new X()), e);
		
		e = parser.makeExpression("sin( x + y )");
		assertEquals(new Sin(new Addition(new X(), new Y())), e);
	}
    @Test
    void testParseAssignment() {
        // Create a test case for Assignment
        ExpressionTreeNode actual = parser.makeExpression("a=[1, 0, 0]");
        Assignment assignment = new Assignment(new Variable("a"), new RGBColor(1, 0, 0));
        assignment.evaluate(0, 0);
        assertEquals(new RGBColor(1, 0, 0), actual.evaluate(0, 0)); 
        assertEquals(assignment, actual);
        
        actual = parser.makeExpression("a");  
        assertEquals(actual, new RGBColor(1, 0, 0));
        // Test addition
        actual = parser.makeExpression("b=x+y");
        Assignment assign2 = new Assignment(new Variable("b"), new Addition(new X(), new Y()));
        assertEquals(actual, assign2);
    }	
	
}
