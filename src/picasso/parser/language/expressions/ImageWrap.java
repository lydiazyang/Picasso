package picasso.parser.language.expressions;

import java.awt.Dimension;
import java.io.File;

import javax.imageio.ImageIO;

import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the image wrap function in the Picasso language.
 * 
 * @author Han Huynh
 * 
 */
public class ImageWrap extends MultiArgumentFunction {
	Pixmap image;
	double dimensionHeight = 600;
	int domainMin = -1;
	int domainMax = 1;

	/**
	 * Create an image wrap  expression that takes as a parameter the given expressions
	 * 
	 * @param param the expression to floor
	 */
	public ImageWrap(Pixmap image, ExpressionTreeNode xParam, ExpressionTreeNode yParam) {
		super(xParam, yParam);
		this.image = image;
	}

	/**
	 * Evaluates the color at the given x,y point by evaluating the coordinates of
	 * the function's parameters
	 * 
	 * @return the color from evaluating the color of the expression's parameters
	 */
	@Override
	public RGBColor evaluate(double x, double y) {
		// evaluate x and y with corresponding expressions
		RGBColor xResult  = xParam.evaluate(x, y);
		RGBColor yResult  = yParam.evaluate(x, y);
		
		// get x and y coords after evaluating expressions AND wrap them
		double xCoordWrap  = wrap(xResult.getBlue());
		double  yCoordWrap = wrap(yResult.getBlue());
		
		// convert coordinates from domain to image scale
		int xCoord = domainToImageScale(xCoordWrap, dimensionHeight);
		int yCoord = domainToImageScale(yCoordWrap, dimensionHeight);
		
		// get color from coordinates in image
		return new RGBColor(image.getColor(xCoord, yCoord));
	}
	
	/**
	 * Helper function to wrap given double value to be within [-1, 1].
	 * 
	 * @return the wrapped value of the given double
	 */
	private double wrap(double value) {
		int max = 1;
		int min = -1;
		double wrappedVal = value;
		if (Math.abs(value) > max) {
			double absVal = Math.abs(value);
			//make wv the residual 
			wrappedVal = absVal % max;
			
			// if odd then should be -(max - residual), else just residual
			if (Math.floor(absVal)%2 != 0) {
				wrappedVal = -(max - wrappedVal);
			}
			
			// if less than min then negate
			if (value < min) {
				wrappedVal = -wrappedVal;
			}
		}
		return wrappedVal;
	}
	
	/**
	 * Helper function to convert given values (in domain space, [-1, 1]) to image space 
	 * 
	 */
	private int domainToImageScale(double value, double bounds) {
		int range = domainMax - domainMin;
		return  (int) (((value - domainMin)/range)*bounds);
		

	}

}
