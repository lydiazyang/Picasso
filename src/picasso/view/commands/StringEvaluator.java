package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;


import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

/// Get string expression
// Randomly generate from each character in string
// Map it into each pixel

public class StringEvaluator implements Command<Pixmap> { hi
    public static final double DOMAIN_MIN = -1;
    public static final double DOMAIN_MAX = 1;
    private static final String[] VARIABLES = {"x", "y", "Color", "Constant"};
    private static final String[] BINARYOPERATORS = {"+", "*", "%", "/", "-"};
    private static final String[] UNARYOPERATORS = {"sin", "cos", "tan", "atan", "exp", "ceil", "floor", "wrap", "abs", "clamp"};
	private int depth;
	
    
    @Override
    public void execute(Pixmap target) {
            // Generate a new random function each time execute is called
    		Random randomNum = new Random();
    		this.depth = randomNum.nextInt(20);
            String randomFunction = generateRandomExpression(depth, true);
            
            if (!randomFunction.startsWith("//")) {
                ExpressionTreeNode expr = createExpression(randomFunction.replaceAll("\"", ""));
                
                // Evaluate it for each pixel
                Dimension size = target.getSize();
                for (int imageY = 0; imageY < size.height; imageY++) {
                    double evalY = imageToDomainScale(imageY, size.height);
                    for (int imageX = 0; imageX < size.width; imageX++) {
                        double evalX = imageToDomainScale(imageX, size.width);
                        Color pixelColor = expr.evaluate(evalX, evalY).toJavaColor();
                        target.setColor(imageX, imageY, pixelColor);
                    }
                }
            }
            } 
        
    
    public static String generateRandomExpression(int depth, boolean topLevel) {
        String expression = buildRandomExpression(depth, topLevel);
        System.out.println("Random Expression: " + expression); // Print the generated expression before returning
        return expression;
    }

    private static String buildRandomExpression(int depth, boolean topLevel) {
        StringBuilder expression = new StringBuilder();

        if (depth <= 0 || (topLevel && depth <= 1)) {
            String randVariable = VARIABLES[new Random().nextInt(VARIABLES.length)];
            Random random = new Random();
            if ("Color".equals(randVariable)) {
                double scale = Math.pow(10, 3);
                Double red = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;
                Double green = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;
                Double blue = Math.round((random.nextDouble() * 2 - 1) * scale) / scale;

                String randColor = "[" + red.toString() + "," + green.toString() + "," + blue.toString() + "]";
                expression.append(randColor);
            } else if ("Constant".equals(randVariable)) {
                Double randInt = random.nextDouble() * 2 - 1;
                expression.append(randInt.toString());
            } else {
                expression.append(randVariable);
            }
        } else {
            String operator;
            int numOperands;

            if (topLevel || new Random().nextBoolean()) {
                operator = UNARYOPERATORS[new Random().nextInt(UNARYOPERATORS.length)];
                numOperands = 1;
                expression.append(operator).append("(");
            } else {
                operator = BINARYOPERATORS[new Random().nextInt(BINARYOPERATORS.length)];
                numOperands = 2;
                expression.append("(");
            }

            for (int i = 0; i < numOperands; i++) {
                expression.append(buildRandomExpression(depth - 1, !topLevel));
                if (i < numOperands - 1) {
                	if (numOperands == 2) {
                		expression.append(operator);
                	} else {
                		expression.append(",");
                	}
                }
            }

            expression.append(")");
        }

        return expression.toString();
    }






    
    /**
     * Convert from image space to domain space.
     */
    protected double imageToDomainScale(int value, int bounds) {
        double range = DOMAIN_MAX - DOMAIN_MIN;
        return ((double) value / bounds) * range + DOMAIN_MIN;
    }
    
    /**
     * A place holder for a more interesting way to build the expression.
     */
    ExpressionTreeNode createExpression(String function) {
        ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
        return expTreeGen.makeExpression(function);
    }
}
