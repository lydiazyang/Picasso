package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

/**
 * Evaluate a random expression for an image.
 * 
 * @author Jenna Bernstein
 */
public class RandomEvaluator implements Command<Pixmap> {
    public static final double DOMAIN_MIN = -1;
    public static final double DOMAIN_MAX = 1;
    private static final String[] VARIABLES = {"x", "y", "Color", "Constant"};
    private static final String[] BINARYOPERATORS = {"+", "*", "%", "/", "-", "^"};
    private static final String[] UNARYOPERATORS = {"!","sin", "log", "cos", "tan", "atan", "exp", "ceil", "floor", 
    		"wrap", "abs", "clamp"};
    private static final String[] MULTIARGUMENTFUNCTIONS = {"perlinBW", "perlinColor", "rgbToYCrCb", "yCrCbToRGB"};
	private int depth;
	private JTextField functionTextField;
	private Evaluator evaluator;
	/**
     * Constructs a RandomEvaluator with the specified text field and evaluator.
     * 
     * @param functionTextField the text field to display the generated expression
     * @param evaluator         the evaluator to execute the generated expression
     */
    public RandomEvaluator(JTextField functionTextField, Evaluator evaluator) {
        this.functionTextField = functionTextField;
        this.evaluator = evaluator;
    }

    /**
     * Executes the random expression generation and evaluation for the given target pixmap.
     * The generated expression is displayed in the text field.
     * 
     * @param target the pixmap to apply the generated expression
     */
    @Override
    public void execute(Pixmap target) {
        Random randomNum = new Random();
        this.depth = randomNum.nextInt(20);
        String randomFunction = generateRandomExpression(depth, true);
        functionTextField.setText(randomFunction);
        evaluator.execute(target, randomFunction);
    }

    /**
     * Generates a random mathematical expression.
     * 
     * @param depth     the depth of the expression tree
     * @param topLevel  flag indicating whether the expression is at the top level
     * @return the generated random expression
     */
    public static String generateRandomExpression(int depth, boolean topLevel) {
        String expression = buildRandomExpression(depth, topLevel);
        System.out.println("Random Expression: " + expression); // Print the generated expression before returning
        return expression;
    }

    /**
     * Builds a random mathematical expression recursively based on the given depth.
     * 
     * @param depth    the depth of the expression tree
     * @param topLevel flag indicating whether the expression is at the top level
     * @return the generated random expression
     */
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
            	if (new Random().nextBoolean()) {
            	    operator = BINARYOPERATORS[new Random().nextInt(BINARYOPERATORS.length)];
            	} else {
            	    operator = MULTIARGUMENTFUNCTIONS[new Random().nextInt(MULTIARGUMENTFUNCTIONS.length)];
            	    expression.append(operator);
            	}
            	numOperands = 2;
                expression.append("(");
            }

            for (int i = 0; i < numOperands; i++) {
                expression.append(buildRandomExpression(depth - 1, !topLevel));
                if (i < numOperands - 1) {
                	if (numOperands == 2) {
                		List<String> binaryOperatorsList = Arrays.asList(BINARYOPERATORS);
                		if (binaryOperatorsList.contains(operator)) {
                			expression.append(operator);
                		}
                		else {
                    		expression.append(",");
                    	}
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
    
}