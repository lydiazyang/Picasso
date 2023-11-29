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
		
		// test "normal" cases
		assertEquals(new RGBColor(0, 0, 0), myTree.evaluate(0, -1));
		assertEquals(new RGBColor(1, 1, 1), myTree.evaluate(1, -1));
		assertEquals(new RGBColor(-1, -1, -1), myTree.evaluate(-1, -1));
		assertEquals(new RGBColor(-.5, -.5, -.5), myTree.evaluate(1.5, -1));
		assertEquals(new RGBColor(.5, .5, .5), myTree.evaluate(-1.5, -1));
		
		// test all ints from -60 to 60
		
		// test doubles 
		double[] tests = {-1.66, -.34, .7888, 5.7222};
		for (double testVal : tests) {
			double wrappedTestVal = testVal;
			if (Math.abs(testVal) > 1) {
				wrappedTestVal = testVal % 1;
				// insert code to figure out how many times testVal needs to be "wrapped"
				// -1.xx -> .xx
				// 1.xx -> -.xx
				// -2.xx -> -.xx
				// 2.xx -> .xx
			}
			System.out.println(testVal + ": " + wrappedTestVal);
			assertEquals(new RGBColor(wrappedTestVal, wrappedTestVal, wrappedTestVal), myTree.evaluate(testVal, -1));
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