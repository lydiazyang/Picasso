package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents perlinColor function
 * 
 * @author Lydia Yang
 */
public class PerlinColor extends MultiArgumentFunction {

    public PerlinColor(ExpressionTreeNode xParam, ExpressionTreeNode yParam) {
        super(xParam, yParam);
    }

    /**
    * Generate Perlin noise based on the given values. Algorithm described in
    * detail at this site:
    * http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
    */
	@Override
	public RGBColor evaluate(double x, double y) {
		// evaluate x and y with corresponding expressions
		RGBColor left  = xParam.evaluate(x, y);
		RGBColor right  = yParam.evaluate(x, y);
		
        double red = ImprovedNoise.noise(left.getRed() + 0.3, right.getRed() + 0.3, 0);
        double blue = ImprovedNoise.noise(left.getBlue() + 0.1, right.getBlue() + 0.1, 0);
        double green = ImprovedNoise.noise(left.getGreen() - 0.8, right.getGreen() - 0.8, 0);
        return new RGBColor(red, green, blue);
	}
}