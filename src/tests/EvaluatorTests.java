/**
 * 
 */
package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.*;

/**
 * Tests of the evaluation of expression trees
 * 
 * @author Sara Sprenkle
 * 
 */
public class EvaluatorTests {

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
		Ceil myTree = new Ceil(new X());

		// some straightforward tests
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.3, 1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(.999, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-.7, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1.5, -1));

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
	public void testWrapEvaluation() {
		Wrap myTree = new Wrap(new X());
		int min = -1;
		int max = 1;
		
		// test "normal" cases
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(-.5, -.5, -.5), myTree.evaluate(1.5, -1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(-1.5, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(2, -1));
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(-2, -1));
		
		// test first 20 ints more than max 
		for (int i = max+1; i <= 20; i++) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = 1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), myTree.evaluate(i, i));
		}
		
		// ... or less than min!
		for (int i = min-1; i >= -20; i--) {
			int wrappedVal = 0;
			if (i%2 != 0) {
				wrappedVal = -1;
			}
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), myTree.evaluate(i, -i));
			assertEquals(new RGBColor(wrappedVal, wrappedVal, wrappedVal), myTree.evaluate(i, i));
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
			assertEquals(new RGBColor(wrappedTestVal, wrappedTestVal, wrappedTestVal), myTree.evaluate(testVal, -1));
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
		
		// Expression Input
		//myTree = new Sin(new Plus(new X(), new Y()));
	    //assertEquals(new RGBColor(Math.sin(1 + 2), Math.sin(1 + 2), Math.sin(1 + 2)), myTree.evaluate(1, 2));
	    
		// Recursion: sin(sin(x))
	    myTree = new Sin(new Sin(new X()));
	    for (double angle = -2 * Math.PI; angle <= 2 * Math.PI; angle += Math.PI / 4) {
	        assertEquals(new RGBColor(Math.sin(Math.sin(angle)), Math.sin(Math.sin(angle)), Math.sin(Math.sin(angle))),
	                myTree.evaluate(angle, 0));
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
	


	private void assertEquals(RGBColor expected, RGBColor actual) {
		if (expected == null || actual == null) {
			throw new IllegalArgumentException("RGB cannot be null");
		}
		else if (expected.getBlue() != actual.getBlue() || expected.getRed() != actual.getRed() || expected.getGreen() != actual.getGreen()){
			throw new AssertionError("RGB values are not equal");
		}
	}

}