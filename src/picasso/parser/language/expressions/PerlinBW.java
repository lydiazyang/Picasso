package picasso.parser.language.expressions;

import picasso.model.ImprovedNoise;
import picasso.parser.language.ExpressionTreeNode;

/**
 * Represents perlinBW function
 * 
 * @author Lydia Yang
 */
public class PerlinBW extends MultiArgumentFunction {
    /**
	 * Create a perlinBW expression that takes as a parameter the given expression
	 * 
	 * @param param the expression to floor
	 */
    public PerlinBW(ExpressionTreeNode xParam, ExpressionTreeNode yParam) {
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
		
        double grey = ImprovedNoise.noise(left.getRed() + right.getRed(), left.getGreen() + right.getGreen(), 
                        left.getBlue() + right.getBlue());
		
        return new RGBColor(grey, grey, grey);
	}
}