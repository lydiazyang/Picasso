package picasso.parser.language.expressions;

import picasso.model.Pixmap;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents the image clip function in the Picasso language.
 * 
 * @author Han Huynh
 * 
 */
public class ImageClip extends MultiArgumentFunction {
	Pixmap image;
	double dimensionHeight;
	double dimensionWidth;
	int domainMin = -1;
	int domainMax = 1;

	/**
	 * Create an image clip expression that takes as a parameter the given expressions
	 * 
	 * @param image, the image file
	 * @param xParam, the expression to evaluate the x coord
	 * @param yParam, the expression to evaluate the y coord
	 */
	public ImageClip(Pixmap image, ExpressionTreeNode xParam, ExpressionTreeNode yParam) {
		super(xParam, yParam);
		this.image = image;
		dimensionHeight = image.getSize().getHeight();
		dimensionWidth = image.getSize().getWidth();
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
		
		// get x and y coords after evaluating expressions AND clamp them
		double xCoordClamp = clamp(xResult.getBlue(), -1, 1);
		double  yCoordClamp = clamp(yResult.getBlue(), -1, 1);
		
		// convert coordinates from domain to image scale
		int xCoord = (int) clamp(domainToImageScale(xCoordClamp, dimensionWidth), 0, dimensionWidth-1);
		int yCoord = (int) clamp(domainToImageScale(yCoordClamp, dimensionHeight), 0, dimensionHeight-1);
		
		return new RGBColor(image.getColor(xCoord, yCoord));
	}
	
	/**
	 * Helper function to clamp given double value to be within [-1, 1].
	 * 
	 * @return the clamped value of the given double, min, and max
	 */
	private double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}
	
	
	/**
	 * Helper function to convert given values (in domain space, [-1, 1]) to image space 
	 * @return the scaled value of the given double and bounds
	 */
	private int domainToImageScale(double value, double bounds) {
		int range = domainMax - domainMin;
		return  (int) (((value - domainMin)/range)*bounds);
		

	}

}
