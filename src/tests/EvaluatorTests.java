/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JTextField;
import java.io.File;
import java.nio.file.Path;
import java.util.Stack;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.model.ImprovedNoise;
import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;
import picasso.view.commands.Evaluator;
import picasso.view.commands.StringEvaluator;
import picasso.parser.language.operators.Addition;
import picasso.parser.language.operators.Assignment;
import picasso.parser.language.operators.Multiply;
import picasso.parser.language.operators.Negate;
import picasso.parser.tokens.IdentifierToken;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.AdditionToken;


/**
 * Tests of the evaluation of expression trees
 * 
 * @author Sara Sprenkle
 * 
 */
public class EvaluatorTests {

	@SuppressWarnings("unused")
	private static final JTextField JTextField = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testConstantEvaluation() {
		ExpressionTreeNode e = new RGBColor(1, -1, 1);
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(1, -1, 1), e.evaluate(i, i));
		}
	}

	@Test
	public void testXEvaluation() {
		X x = new X();
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), x.evaluate(i, i));
		}
	}

	@Test
	public void testFloorEvaluation() {
		Floor myTree = new Floor(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(.4, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(.999, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-.7, -1));

		// test the ints; remember that y's value doesn't matter
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}

		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal : tests) {
			double floorOfTestVal = Math.floor(testVal);
			assertEquals(new RGBColor(floorOfTestVal, floorOfTestVal, floorOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(floorOfTestVal, floorOfTestVal, floorOfTestVal),
					myTree.evaluate(testVal, testVal));
		}
	}
	
	
	@Test
	public void testCeilEvaluation() {
		//Testing x
		Ceil myTree = new Ceil(new X());

		// some straightforward tests
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.3, 1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.999, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-.7, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1.5, -1));
		
		//Testing y
		Ceil yTree = new Ceil(new Y());
		
		// some straightforward tests
		assertEquals(new RGBColor(1, 1, 1), yTree.evaluate(1, .3));
		assertEquals(new RGBColor(1, 1, 1), yTree.evaluate(-1, .999));
		assertEquals(new RGBColor(0, 0, 0), yTree.evaluate(-1, -.7));
		assertEquals(new RGBColor(-1, -1, -1), yTree.evaluate(-1, -1.5));
		
		// test the ints
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}

		// test doubles 
		double[] tests = { -.7, -.00001, .000001, .5 };

		for (double testVal1 : tests) {
			double ceilOfTestVal = Math.ceil(testVal1);
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal), myTree.evaluate(testVal1, -1));
			assertEquals(new RGBColor(ceilOfTestVal, ceilOfTestVal, ceilOfTestVal), myTree.evaluate(testVal1, testVal1));
		}
	}
	
	@Test
	public void testAbsEvaluation() {
		Abs myTree = new Abs(new X());
		
		//straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(0.1, 0.1, 0.1), myTree.evaluate(0.1, -1));
		assertEquals(new RGBColor(0.1, 0.1, 0.1), myTree.evaluate(-0.1, -1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(0.5, -1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(-0.5, -1));
		
		//test all integers
		for (int i = -1; i <= 1; i++) {
			i = Math.abs(i);
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(i, i, i), myTree.evaluate(i, i));
		}
		
		//test doubles
		double[] tests = {-.7, -0.00001, 1.3, -4.9999};
		for (double testVal : tests) {
			double absOfTestVal = Math.abs(testVal);
			assertEquals(new RGBColor(absOfTestVal, absOfTestVal, absOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(absOfTestVal, absOfTestVal, absOfTestVal), myTree.evaluate(testVal, testVal));
			assertEquals(new RGBColor(absOfTestVal, absOfTestVal, absOfTestVal), myTree.evaluate(testVal, 1));
		}
	}
	
	
	@Test
	public void testExpEvaluation() {
	    Exp myTree = new Exp(new X());

	    // Straightforward tests
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(0, -1));
	    assertEquals(new RGBColor(Math.exp(1), Math.exp(1), Math.exp(1)), myTree.evaluate(1, -1));
	    assertEquals(new RGBColor(Math.exp(-1), Math.exp(-1), Math.exp(-1)), myTree.evaluate(-1, -1));
	    assertEquals(new RGBColor(Math.exp(0.1), Math.exp(0.1), Math.exp(0.1)), myTree.evaluate(0.1, -1));
	    assertEquals(new RGBColor(Math.exp(-0.1), Math.exp(-0.1), Math.exp(-0.1)), myTree.evaluate(-0.1, -1));
	    assertEquals(new RGBColor(Math.exp(0.5), Math.exp(0.5), Math.exp(0.5)), myTree.evaluate(0.5, -1));
	    assertEquals(new RGBColor(Math.exp(-0.5), Math.exp(-0.5), Math.exp(-0.5)), myTree.evaluate(-0.5, -1));

	    // Test all integers
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, -i));
	        assertEquals(new RGBColor(Math.exp(i), Math.exp(i), Math.exp(i)), myTree.evaluate(i, i));
	    }

	    // Test doubles
	    double[] tests = { -0.7, -0.00001, 1.3, -4.9999 };
	    for (double testVal : tests) {
	    	assertEquals(new RGBColor(Math.exp(testVal), Math.exp(testVal), Math.exp(testVal)), myTree.evaluate(testVal, -1));
	        assertEquals(new RGBColor(Math.exp(testVal), Math.exp(testVal), Math.exp(testVal)), myTree.evaluate(testVal, testVal));
	        assertEquals(new RGBColor(Math.exp(testVal), Math.exp(testVal), Math.exp(testVal)),	myTree.evaluate(testVal, 1));
	    }
	}

	
	
	@Test
	public void testWrapEvaluation() {
		int min = -1;
		int max = 1;
		
		// testing only x values
		Wrap xTree = new Wrap(new X());
		
		// test "normal" cases
		assertEquals(new RGBColor(0, 0, 0), xTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), xTree.evaluate(1, -1));
		assertEquals(new RGBColor(-1, -1, -1), xTree.evaluate(-1, -1));
		assertEquals(new RGBColor(-.5, -.5, -.5), xTree.evaluate(1.5, -1));
		assertEquals(new RGBColor(.5, .5, .5), xTree.evaluate(-1.5, -1));
		assertEquals(new RGBColor(0, 0, 0), xTree.evaluate(2, -1));
		assertEquals(new RGBColor(0, 0, 0), xTree.evaluate(-2, -1));
		
		// test first 10 ints more than max 
		for (int i = max+1; i <= 10; i++) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = 1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), xTree.evaluate(i, -i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), xTree.evaluate(i, i));
		}
		
		// ... or less than min!
		for (int i = min-1; i >= -10; i--) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = -1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), xTree.evaluate(i, -i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), xTree.evaluate(i, i));
		}
		
		// test doubles 
		double[] tests = {-1.66, -.34, .7888, 5.7222};		
		for (double testVal : tests) {
			double wrappedTestVal = testVal;
			
			// check if value is more than min or max of (-)1
			if (Math.abs(testVal) > max) {
				double absTestVal = Math.abs(testVal);
				//make WTV the residual 
				wrappedTestVal = absTestVal % max;
				
				// if odd then should be -(max - residual), else just residual
				if (Math.floor(absTestVal)%2 != 0) {
					wrappedTestVal = -(max - wrappedTestVal);
				}
				
				// if less than min then negate
				if (testVal < min) {
					wrappedTestVal = -wrappedTestVal;
				}
				
			}
			assertEquals(new RGBColor(wrappedTestVal, wrappedTestVal, wrappedTestVal), xTree.evaluate(testVal, -1));
		}
		
		// testing only y values
		Wrap yTree = new Wrap(new Y());
		
		// test "normal" cases
		assertEquals(new RGBColor(0, 0, 0), yTree.evaluate(-1, 0));
		assertEquals(new RGBColor(1, 1, 1), yTree.evaluate(-1, 1));
		assertEquals(new RGBColor(-1, -1, -1), yTree.evaluate(-1, -1));
		assertEquals(new RGBColor(-.5, -.5, -.5), yTree.evaluate(-1, 1.5));
		assertEquals(new RGBColor(.5, .5, .5), yTree.evaluate(-1, -1.5));
		assertEquals(new RGBColor(0, 0, 0), yTree.evaluate(-1, 2));
		assertEquals(new RGBColor(0, 0, 0), yTree.evaluate(-1, -2));

		// test first 10 ints more than max 
		for (int i = max+1; i <= 10; i++) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = 1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), yTree.evaluate(-i, i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), yTree.evaluate(i, i));
		}
		
		// ... or less than min!
		for (int i = min-1; i >= -10; i--) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = -1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), yTree.evaluate(-i, i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), yTree.evaluate(i, i));
		}
		
		// test doubles 
		for (double testVal : tests) {
			double wrappedTestVal = testVal;
			
			// check if value is more than min or max of (-)1
			if (Math.abs(testVal) > max) {
				double absTestVal = Math.abs(testVal);
				//make WTV the residual 
				wrappedTestVal = absTestVal % max;
				
				// if odd then should be -(max - residual), else just residual
				if (Math.floor(absTestVal)%2 != 0) {
					wrappedTestVal = -(max - wrappedTestVal);
				}
				
				// if less than min then negate
				if (testVal < min) {
					wrappedTestVal = -wrappedTestVal;
				}
				
			}
			assertEquals(new RGBColor(wrappedTestVal, wrappedTestVal, wrappedTestVal), yTree.evaluate(-1, testVal));
		}
	}
	
	@Test
	public void testClampEvaluation() {
		Clamp myTree = new Clamp(new X());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1.5, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-2, -1));

		// test the  negative ints below -1; remember that y's value doesn't matter
		for (int i = -10; i < 0; i++) {
			assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(i, -i));
		}
		
		// test the  positive ints above 1; remember that y's value doesn't matter
		for (int i = 1; i < 10; i++) {
			assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(i, -i));
		}

		double[] tests = { -.7, -2.4, 1, 1.5, 0, .2, -.7 };

		for (double testVal : tests) {
			if (testVal > 1) {
				assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(testVal, -1));
			}
			else if (testVal < -1) {
				assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(testVal, -1));
			}
			else {
				assertEquals(new RGBColor(testVal, testVal, testVal),
						myTree.evaluate(testVal, testVal));
			}
			
		}

		Clamp myOtherTree = new Clamp(new Y());

		// some straightforward tests
		assertEquals(new RGBColor(0, 0, 0), myOtherTree.evaluate(-1, 0));
		assertEquals(new RGBColor(1, 1, 1), myOtherTree.evaluate(-1, 1.5));
		assertEquals(new RGBColor(-1, -1, -1), myOtherTree.evaluate(-1, -2));

		// test the  negative ints below -1; remember that y's value doesn't matter
		for (int i = -10; i < 0; i++) {
			assertEquals(new RGBColor(-1, -1, -1), myOtherTree.evaluate(-i, i));
		}
		
		// test the  positive ints above 1; remember that y's value doesn't matter
		for (int i = 1; i < 10; i++) {
			assertEquals(new RGBColor(1, 1, 1), myOtherTree.evaluate(-i, i));
		}

		double[] tests2 = { -.7, -2.4, 1, 1.5, 0, .2, -.7 };

		for (double testVal : tests2) {
			if (testVal > 1) {
				assertEquals(new RGBColor(1, 1, 1), myOtherTree.evaluate(-1, testVal));
			}
			else if (testVal < -1) {
				assertEquals(new RGBColor(-1, -1, -1), myOtherTree.evaluate(-1, testVal));
			}
			else {
				assertEquals(new RGBColor(testVal, testVal, testVal),
						myOtherTree.evaluate(testVal, testVal));
			}
			
		}
	}
	
	@Test
	public void testNegateEvaluation() {
	    Negate myTree = new Negate(new X());

	    // some straightforward tests
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(1, -1));
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(-1, 1));

	    // test various double values
	    double[] tests = { -0.7, -.2, 1, 0, 0.2, -0.7 };

	    for (double testVal : tests) {
	        assertEquals(new RGBColor(-testVal, -testVal, -testVal), myTree.evaluate(testVal, testVal));
	    }

	    Negate myOtherTree = new Negate(new Y());

	    // some straightforward tests
	    assertEquals(new RGBColor(0, 0, 0), myOtherTree.evaluate(-1, 0));
	    assertEquals(new RGBColor(-1, -1, -1), myOtherTree.evaluate(-1, 1));
	    assertEquals(new RGBColor(1, 1, 1), myOtherTree.evaluate(1, -1));

	    // test various double values
	    double[] tests2 = { -0.7, -.2, 1, 0, 0.2, -0.7 };

	    for (double testVal : tests2) {
	        assertEquals(new RGBColor(-testVal, -testVal, -testVal), myOtherTree.evaluate(-1, testVal));
	    }
	}
	
	@Test
	public void testLogEvaluation() {
	    Log myTree = new Log(new X());

	    // some straightforward tests
	    assertEquals(new RGBColor(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), myTree.evaluate(0, 0));
	    assertEquals(new RGBColor(Double.NaN, Double.NaN, Double.NaN), myTree.evaluate(-1, 2));

	    // test various double values
	    double[] tests = { 0.5, 2, 1, 1.5, 3, 0.2, 0.7 };

	    for (double testVal : tests) {
	        assertEquals(new RGBColor(Math.log(testVal), Math.log(testVal), Math.log(testVal)), myTree.evaluate(testVal, testVal));
	    }

	    Log myOtherTree = new Log(new Y());

	    // some straightforward tests
	    assertEquals(new RGBColor(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY), myOtherTree.evaluate(0, 0));
	    assertEquals(new RGBColor(Double.NaN, Double.NaN, Double.NaN), myOtherTree.evaluate(2, -1));

	    // test various double values
	    double[] tests2 = { 0.5, 2, 1, 1.5, 3, 0.2, 0.7 };

	    for (double testVal : tests2) {
	        assertEquals(new RGBColor(Math.log(testVal), Math.log(testVal), Math.log(testVal)), myOtherTree.evaluate(testVal, testVal));
	    }
	}


	
	@Test
	public void testEvaluatorException() {
		JTextField input = new JTextField();
		
	    boolean thrown = true;

	    try {
			Evaluator evaluator = new Evaluator(input);
			evaluator.execute(null);
	    } catch (Exception e) {
	        thrown = false;
	    }

	    assertTrue(thrown);
	}
		
	
	@Test
	public void testSinEvaluation() {
		Sin myTree;

		// Basic input: sin(0) = 0
		myTree = new Sin(new RGBColor(0,0,0));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0,0));
		
		// Common input: sin(pi/2) =  1
		myTree = new Sin(new RGBColor(Math.PI / 2, Math.PI / 2, Math.PI / 2));
		assertEquals(new RGBColor(1,1,1), myTree.evaluate(Math.PI / 2,0));
		
		// Negative input: sin(-pi/2) = -1
		myTree = new Sin(new RGBColor(-Math.PI / 2, -Math.PI / 2, -Math.PI / 2));
		assertEquals(new RGBColor(-1,-1,-1), myTree.evaluate(-Math.PI / 2,0));
		
		// Variable Input: sin(x)
		myTree = new Sin(new X());
		for (int i = -1; i <= 1; i++) {
			assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)), myTree.evaluate(i, i));		
		}
		
		// Variable Input: sin(y)
				myTree = new Sin(new Y());
				for (int i = -1; i <= 1; i++) {
					assertEquals(new RGBColor(Math.sin(i), Math.sin(i), Math.sin(i)), myTree.evaluate(i, i));		
				}
			    
		// Recursion: sin(sin(y))
	    myTree = new Sin(new Sin(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        assertEquals(new RGBColor(Math.sin(Math.sin(angle)), Math.sin(Math.sin(angle)), Math.sin(Math.sin(angle))),
	                myTree.evaluate(0, angle));
	    }
	    
	    // Double tests
		double[] tests = { -.7, -.00001, .000001, .5 };
		myTree = new Sin(new X());
		for (double testVal : tests) {
			double sinOfTestVal = Math.sin(testVal);
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal), myTree.evaluate(testVal, -1));
			assertEquals(new RGBColor(sinOfTestVal, sinOfTestVal, sinOfTestVal),
					myTree.evaluate(testVal, testVal));
		}

	}
	
	@Test
	public void testCosEvaluation() {
	    Cos myTree;

	    // Basic input: cos(0) = 1
	    myTree = new Cos(new RGBColor(0, 0, 0));
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(0, 0));

	    // Common input: cos(pi/2) = 0
	    myTree = new Cos(new RGBColor(Math.PI / 2, Math.PI / 2, Math.PI / 2));
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(Math.PI / 2, 0));

	    // Negative input: cos(-pi/2) = 0
	    myTree = new Cos(new RGBColor(-Math.PI / 2, -Math.PI / 2, -Math.PI / 2));
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-Math.PI / 2, 0));

	    // Variable Input: cos(x)
	    myTree = new Cos(new X());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.cos(i), Math.cos(i), Math.cos(i)), myTree.evaluate(i, i));
	    }

	    // Variable Input: cos(y)
	    myTree = new Cos(new Y());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.cos(i), Math.cos(i), Math.cos(i)), myTree.evaluate(i, i));
	    }

	    // Recursion: cos(cos(y))
	    myTree = new Cos(new Cos(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        assertEquals(new RGBColor(Math.cos(Math.cos(angle)), Math.cos(Math.cos(angle)), Math.cos(Math.cos(angle))),
	                myTree.evaluate(0, angle));
	    }

	    // Double tests
	    double[] tests = { -.7, -.00001, .000001, .5 };
	    myTree = new Cos(new X());
	    for (double testVal : tests) {
	        double cosOfTestVal = Math.cos(testVal);
	        assertEquals(new RGBColor(cosOfTestVal, cosOfTestVal, cosOfTestVal), myTree.evaluate(testVal, -1));
	        assertEquals(new RGBColor(cosOfTestVal, cosOfTestVal, cosOfTestVal),
	                myTree.evaluate(testVal, testVal));
	    }
	}

	
	@Test
	public void testTanEvaluation() {
	    Tan myTree;

	    // Basic input: tan(0) = 0
	    myTree = new Tan(new RGBColor(0, 0, 0));
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));

	    // Common input: tan(pi/4) = 1
	    myTree = new Tan(new RGBColor(Math.PI / 4, Math.PI / 4, Math.PI / 4));
	    assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(Math.PI / 4, 0));

	    // Negative input: tan(-pi/2) = -1
	    myTree = new Tan(new RGBColor(-Math.PI / 4, -Math.PI / 4, -Math.PI / 4));
	    assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-Math.PI / 4, 0));

	    // Variable Input: tan(x)
	    myTree = new Tan(new X());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.tan(i), Math.tan(i), Math.tan(i)), myTree.evaluate(i, i));
	    }

	    // Variable Input: tan(y)
	    myTree = new Tan(new Y());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.tan(i), Math.tan(i), Math.tan(i)), myTree.evaluate(i, i));
	    }

	    // Recursion: tan(sin(y))
	    myTree = new Tan(new Sin(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        assertEquals(new RGBColor(Math.tan(Math.sin(angle)), Math.tan(Math.sin(angle)), Math.tan(Math.sin(angle))),
	                myTree.evaluate(0, angle));
	    }

	    // Double tests
	    double[] tests = { -.7, -.00001, .000001, .5 };
	    myTree = new Tan(new X());
	    for (double testVal : tests) {
	        double tanOfTestVal = Math.tan(testVal);
	        assertEquals(new RGBColor(tanOfTestVal, tanOfTestVal, tanOfTestVal), myTree.evaluate(testVal, -1));
	        assertEquals(new RGBColor(tanOfTestVal, tanOfTestVal, tanOfTestVal),
	                myTree.evaluate(testVal, testVal));
	    }
	}

	
	@Test
	public void testAtanEvaluation() {
	    Atan myTree;

	    // Basic input: atan(0) = 0
	    myTree = new Atan(new RGBColor(0, 0, 0));
	    assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, 0));

	    // Common input: atan(1) = pi/4
	    myTree = new Atan(new RGBColor(1, 1, 1));
	    assertEquals(new RGBColor(Math.PI / 4, Math.PI / 4, Math.PI / 4), myTree.evaluate(1, 0));

	    // Negative input: atan(-1) = -pi/4
	    myTree = new Atan(new RGBColor(-1, -1, -1));
	    assertEquals(new RGBColor(-Math.PI / 4, -Math.PI / 4, -Math.PI / 4), myTree.evaluate(-1, 0));

	    // Variable Input: atan(x)
	    myTree = new Atan(new X());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.atan(i), Math.atan(i), Math.atan(i)), myTree.evaluate(i, i));
	    }

	    // Variable Input: atan(y)
	    myTree = new Atan(new Y());
	    for (int i = -1; i <= 1; i++) {
	        assertEquals(new RGBColor(Math.atan(i), Math.atan(i), Math.atan(i)), myTree.evaluate(i, i));
	    }

	    // Recursion: atan(tan(y))
	    myTree = new Atan(new Tan(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        assertEquals(new RGBColor(Math.atan(Math.tan(angle)), Math.atan(Math.tan(angle)), Math.atan(Math.tan(angle))),
	                myTree.evaluate(0, angle));
	    }

	    // Double tests
	    double[] tests = { -.7, -.00001, .000001, .5 };
	    myTree = new Atan(new X());
	    for (double testVal : tests) {
	        double atanOfTestVal = Math.atan(testVal);
	        assertEquals(new RGBColor(atanOfTestVal, atanOfTestVal, atanOfTestVal), myTree.evaluate(testVal, -1));
	        assertEquals(new RGBColor(atanOfTestVal, atanOfTestVal, atanOfTestVal),
	                myTree.evaluate(testVal, testVal));
	    }
	}

	
    @Test
    public void testAssignmentEvaluation() {
    	// Addition Test
        Variable a = new Variable("a");
        ExpressionTreeNode e = new Addition(new X(), new Y());
        // Assign a = x+y
        Assignment assignment = new Assignment(a, e);
        assertEquals(new RGBColor(1, 1, 1), assignment.evaluate(.5, .5));
        
        // Multiplication Test
        Variable u = new Variable("u");
        ExpressionTreeNode g = new Multiply(new X(), new Y());
        // Assign a = x*y
        Assignment assign2 = new Assignment(u, g);
        assertEquals(new RGBColor(.25, .25, .25), assign2.evaluate(.5, .5));
    }
	
	@Test
	public void testImageWrapEvaluation() {
		String filePath = Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + "vortex.jpg";
		Pixmap image = new Pixmap(filePath);
		
		// constant input: (x = 1)
		ImageWrap myTree = new ImageWrap(image, new Constant(1), new Y());
		assertEquals(new RGBColor(image.getColor(scale(1), scale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(scale(1), scale(-1))), myTree.evaluate(-.6, -1));
		assertEquals(new RGBColor(image.getColor(scale(1), scale(-1))), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(image.getColor(scale(1), scale(-1))), myTree.evaluate(.6, -1));
		
		// constant input: (y = 1)
		myTree = new ImageWrap(image, new X(), new Constant(1));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(1))), myTree.evaluate(-1, -.6));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(1))), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(1))), myTree.evaluate(-1, .6));
		
		// variable input: (x = x+x)
		myTree = new ImageWrap(image, new Addition(new X(), new X()), new Y());
		assertEquals(new RGBColor(image.getColor(scale(0), scale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(scale(0), scale(-1))),myTree.evaluate(-2, -1));
		assertEquals(new RGBColor(image.getColor(scale(0), scale(-1))), myTree.evaluate(2, -1));
		assertEquals(new RGBColor(image.getColor(scale(0), scale(-1))), myTree.evaluate(0, -1));
		
		// variable input (y = y+y)
		myTree = new ImageWrap(image, new X(), new Addition(new Y(), new Y()));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(0))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(0))),myTree.evaluate(-1, -2));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(0))), myTree.evaluate(-1, 2));
		assertEquals(new RGBColor(image.getColor(scale(-1), scale(0))), myTree.evaluate(-1, 0));
		
		// sin input (x = sin(x))
		myTree = new ImageWrap(image, new Sin(new X()), new Y());
		assertEquals(new RGBColor(image.getColor(scale(Math.sin(-1)), scale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(scale(Math.sin(-.6)), scale(-1))), myTree.evaluate(-.6, -1));
		assertEquals(new RGBColor(image.getColor(scale(Math.sin(1)), scale(-1))), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(image.getColor(scale(Math.sin(.6)), scale(-1))), myTree.evaluate(.6, -1));
	}

	@Test
	public void testImageClipEvaluation() {
		String filePath = Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + "vortex.jpg";
		Pixmap image = new Pixmap(filePath);
		
		// constant input: (x = 1)
		ImageClip myTree = new ImageClip(image, new Constant(1), new Y());
		assertEquals(new RGBColor(image.getColor(wrapScale(1), wrapScale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(1), wrapScale(-1))), myTree.evaluate(-.6, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(1), wrapScale(-1))), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(1), wrapScale(-1))), myTree.evaluate(.6, -1));
		
		// constant input: (y = 1)
		myTree = new ImageClip(image, new X(), new Constant(1));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(1))), myTree.evaluate(-1, -.6));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(1))), myTree.evaluate(-1, 1));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(1))), myTree.evaluate(-1, .6));
		
		// variable input: (x = x+x)
		myTree = new ImageClip(image, new Addition(new X(), new X()), new Y());
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(-1))),myTree.evaluate(-2, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(1), wrapScale(-1))), myTree.evaluate(2, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(0), wrapScale(-1))), myTree.evaluate(0, -1));
		
		// variable input (y = y+y)
		myTree = new ImageClip(image, new X(), new Addition(new Y(), new Y()));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(-1))),myTree.evaluate(-1, -2));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(1))), myTree.evaluate(-1, 2));
		assertEquals(new RGBColor(image.getColor(wrapScale(-1), wrapScale(0))), myTree.evaluate(-1, 0));
		
		// sin input (x = sin(x))
		myTree = new ImageClip(image, new Sin(new X()), new Y());
		assertEquals(new RGBColor(image.getColor(wrapScale(Math.sin(-1)), wrapScale(-1))), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(Math.sin(-.6)), wrapScale(-1))), myTree.evaluate(-.6, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(Math.sin(1)), wrapScale(-1))), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(image.getColor(wrapScale(Math.sin(.6)), wrapScale(-1))), myTree.evaluate(.6, -1));
	}
	
	@Test
	public void testPerlinBWEvaluation() {
		PerlinBW myTree;

		// Basic input: perlinBW(x, y)
		myTree = new PerlinBW(new X(), new Y());
		for (int i = -1; i <= 1; i++) {
			RGBColor left = new X().evaluate(i, i);
        	RGBColor right = new Y().evaluate(i, i);
			double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
                    left.getBlue() + right.getBlue());
			assertEquals(new RGBColor(grey, grey, grey), myTree.evaluate(i, i));
		}

		
		// Common input: perlinBW(x+x, y)
		myTree = new PerlinBW(new Addition(new X(), new X()), new Y());
	    for (int i = -1; i <= 1; i++) {
	        RGBColor left = new Addition(new X(), new X()).evaluate(i, i);
	        RGBColor right = new Y().evaluate(i, i);
	        double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
	                    left.getBlue() + right.getBlue());
	        assertEquals(new RGBColor(grey, grey, grey), myTree.evaluate(i, i));
	    }
		
		// Common input: perlinBW(x*x, y)
	    myTree = new PerlinBW(new Multiply(new X(), new X()), new Y());
	    for (int i = -1; i <= 1; i++) {
	        RGBColor left = new Multiply(new X(), new X()).evaluate(i, i);
	        RGBColor right = new Y().evaluate(i, i);
	        double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
	                    left.getBlue() + right.getBlue());
	        assertEquals(new RGBColor(grey, grey, grey), myTree.evaluate(i, i));
	    }
		

		// Recursion: perlinBW(sin(x), cos(y))
	    myTree = new PerlinBW(new Sin(new X()), new Cos(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        RGBColor left = new Sin(new X()).evaluate(angle, angle);
	        RGBColor right = new Cos(new Y()).evaluate(angle, angle);
	        double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
	                    left.getBlue() + right.getBlue());
	        assertEquals(new RGBColor(grey, grey, grey), myTree.evaluate(angle, angle));
	    }

		// Double tests
	    myTree = new PerlinBW(new X(), new Y());
	    double[] tests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : tests) {
	        RGBColor left = new X().evaluate(testVal, testVal);
	        RGBColor right = new Y().evaluate(testVal, testVal);
	        double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
	                    left.getBlue() + right.getBlue());
	        assertEquals(new RGBColor(grey, grey, grey), myTree.evaluate(testVal, testVal));
	    }

	}
	
	@Test
	public void testPerlinColorEvaluation() {
		PerlinColor myTree;

		// Basic input: perlinColor(x, y)
		 myTree = new PerlinColor(new X(), new Y());
		for (int i = -1; i <= 1; i++) {
			RGBColor left = new X().evaluate(i, i);
			RGBColor right = new Y().evaluate(i, i);
			double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
		}

		
		// Common input: perlinColor(x+x, y)
		myTree = new PerlinColor(new Addition(new X(), new X()), new Y());
	    for (int i = -1; i <= 1; i++) {
	        RGBColor left = new Addition(new X(), new X()).evaluate(i, i);
	        RGBColor right = new Y().evaluate(i, i);
	        double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
	    }
		
		// Common input: perlinColor(x*x, y)
	    myTree = new PerlinColor(new Multiply(new X(), new X()), new Y());
	    for (int i = -1; i <= 1; i++) {
	        RGBColor left = new Multiply(new X(), new X()).evaluate(i, i);
	        RGBColor right = new Y().evaluate(i, i);
	        double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
	    }
		

		// Recursion: perlinColor(sin(x), cos(y))
	    myTree = new PerlinColor(new Sin(new X()), new Cos(new Y()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        RGBColor left = new Sin(new X()).evaluate(angle, angle);
        	RGBColor right = new Cos(new Y()).evaluate(angle, angle);
	        double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(angle, angle));
	    }

		// Double tests
	    myTree = new PerlinColor(new X(), new Y());
	    double[] tests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : tests) {
	        RGBColor left = new X().evaluate(testVal, testVal);
	        RGBColor right = new Y().evaluate(testVal, testVal);
	        double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
			double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
			double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(testVal, testVal));
	    }

	}

	@Test 
	public void testRgbToYCrCbEvaluation() {
		RgbToYCrCb myTree;
		
		// test with variable x 
		myTree = new RgbToYCrCb(new X());
		for (int i = -1; i <= 1; i++) {
			RGBColor c = new X().evaluate (i, i);
			double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
        	double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
       	 	double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
		}
		
		// test with variable y
		myTree = new RgbToYCrCb(new Y());
		for (int i = -1; i <= 1; i++) {
			RGBColor c = new Y().evaluate (i, i);
			double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
        	double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
       	 	double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
		}
		
		// test with Doubles X
		double[] tests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : tests) {
	        RGBColor c = new X().evaluate(testVal, testVal);
	        double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
        	double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
       	 	double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(testVal, testVal));
	    }

		// test with Doubles Y
		double[] ytests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : ytests) {
	        RGBColor c = new Y().evaluate(testVal, testVal);
	        double red = c.getRed() * 0.2989 + c.getGreen() * 0.5866 + c.getBlue() * 0.1145;
        	double green = c.getRed() * -0.1687 + c.getGreen() * -0.3312 + c.getBlue() * 0.5;
       	 	double blue = c.getRed() * 0.5000 + c.getGreen() * -0.4183 + c.getBlue() * -0.0816;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(testVal, testVal));
	    }
		
	}
	
	@Test 
	public void testYCrCbToRBGEvaluation() {
		YCrCbToRGB myTree;
		
		// test with variable x 
		myTree = new YCrCbToRGB(new X());
		for (int i = -1; i <= 1; i++) {
			RGBColor c = new X().evaluate (i, i);
			double red = c.getRed() + c.getBlue() * 1.4022;
        	double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
        	double blue = c.getRed() + c.getGreen() * 1.7710;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
		}
		
		// test with variable y
		myTree = new YCrCbToRGB(new Y());
		for (int i = -1; i <= 1; i++) {
			RGBColor c = new Y().evaluate (i, i);
			double red = c.getRed() + c.getBlue() * 1.4022;
        	double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
        	double blue = c.getRed() + c.getGreen() * 1.7710;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(i, i));
		}
		
		// test with Doubles X
		double[] tests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : tests) {
	        RGBColor c = new X().evaluate(testVal, testVal);
	        double red = c.getRed() + c.getBlue() * 1.4022;
        	double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
        	double blue = c.getRed() + c.getGreen() * 1.7710;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(testVal, testVal));
	    }

		// test with Doubles Y
		double[] ytests = { -0.7, -0.00001, 0.000001, 0.5 };
	    for (double testVal : ytests) {
	        RGBColor c = new Y().evaluate(testVal, testVal);
	        double red = c.getRed() + c.getBlue() * 1.4022;
        	double green = c.getRed() + c.getGreen() * -0.3456 + c.getBlue() * -0.7145;
        	double blue = c.getRed() + c.getGreen() * 1.7710;
			assertEquals(new RGBColor(red, green, blue), myTree.evaluate(testVal, testVal));
	    }
		
	}

	@Test
    public void testGenerateExpressionFromString() {
        JTextField input = new JTextField();
        Evaluator evaluator = new Evaluator(input);
        StringEvaluator stringEvaluator = new StringEvaluator(input, evaluator);

        // Basic input
        Assert.assertEquals("x + y", stringEvaluator.generateExpressionFromString("ae"));

        // Unary function
        Assert.assertEquals("rgbToYCrCb(x)", stringEvaluator.generateExpressionFromString("A"));

        // Multi-argument function
        Assert.assertEquals("perlinBW(x, y)", stringEvaluator.generateExpressionFromString("B"));

        // Variable
        Assert.assertEquals("x", stringEvaluator.generateExpressionFromString("a"));

        // Complex expression
        Assert.assertEquals("(x * y) + (sin(x) / cos(y))", stringEvaluator.generateExpressionFromString("iaAeO"));

        // Test with empty input
        Assert.assertEquals("0", stringEvaluator.generateExpressionFromString(""));
       

        // Test with favorite input
        Assert.assertEquals("(((perlinColor(x, y) - y) / y) + (perlinColor(x, y) + (y + (exp(x) + ((y - y) + (y + (((((x - y) / perlinColor(x, y)) + x) - y) / x)))))))", stringEvaluator.generateExpressionFromString("Hello Dr. Sprenkle! How are you??"));
    }
	
	private void assertEquals(RGBColor expected, RGBColor actual) {
		double delta = 0.000001;
		if (expected == null || actual == null) {
			throw new IllegalArgumentException("RGB cannot be null");
		}
		else if (Math.abs(expected.getBlue() - actual.getBlue()) > delta ||
	               Math.abs(expected.getRed() - actual.getRed()) > delta ||
	               Math.abs(expected.getGreen() - actual.getGreen()) > delta) {
			throw new AssertionError("RGB values are not equal");
		}
	}
	
	private int scale(double value) {
		return  (int) (((value + 1)/2)*800);
	}
	
	private int wrapScale(double value) {
		return (int) Math.max(0, Math.min(799,scale(value)));
	}

}