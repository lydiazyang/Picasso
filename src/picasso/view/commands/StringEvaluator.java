package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;
import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;

// Not randomly
// use ascii values to figure out assignments
// incorporate unary functions as well
// Don't need to bring color in because perlin can help with colors
/**
 * Evaluate a string expression for each pixel in a image.
 * 
 * @author Lydia Yang
 */
public class StringEvaluator implements Command<Pixmap> {
    private JTextField input;
    private static final String[] VARIABLES = {"x", "y"};
    private static final String[] UNARYOPERATORS = {"sin", "cos", "tan", "atan", "exp", "ceil", "floor", "wrap", "abs", "clamp"};

    /**
     * Constructor for the StringEvaluator class
     *
     * @param input  JTextField from which the input string is obtained.
     */
    public StringEvaluator(JTextField input) {
        this.input = input;
    }


    @Override
    public void execute(Pixmap target) {
        String inputString = input.getText();
        String generatedExpression = generateExpressionFromString(inputString);
        ExpressionTreeNode expr = createExpression(generatedExpression);

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


    /**
     * Generates an expression from the input string.
     *
     * @param input The input string.
     * @return The generated expression.
     */
    private String generateExpressionFromString(String input) {
            Stack<String> operators = new Stack<>();
            Stack<String> operands = new Stack<>();

            for (char c : input.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    operands.push(buildUnaryFunction(c));
                } else if (isOperatorChar(c)) {
                    operators.push(buildOperator(c));
                } else {
                    operands.push(buildVariable(c));
                }

                if (operands.size() > 1 && !operators.isEmpty()) {
                    String combinedExpr = combineExpression(operators, operands);
                    operands.push(combinedExpr);
                }
            }

            while (!operators.isEmpty() && operands.size() > 1) {
                String combinedExpr = combineExpression(operators, operands);
                operands.push(combinedExpr);
            }

            while (operands.size() > 1) {
                operators.push("+"); // Default operator to combine remaining operands
                String combinedExpr = combineExpression(operators, operands);
                operands.push(combinedExpr);
            }

            if (operands.isEmpty()) {
                return "0";
            } else {
                return operands.pop();
            }    
        }


    /**
     * Determines if a character should be treated as an operator.
     *
     * @param c The character to check.
     * @return True if the character is an operator, false otherwise.
     */
    private boolean isOperatorChar(char c) {
        return "aeiou".indexOf(Character.toLowerCase(c)) >= 0;
    }


    /**
     * Builds an operator based on a character.
     *
     * @param c The character.
     * @return The operator as a string.
     */
    private String buildOperator(char c) {
        char lowerCaseChar = Character.toLowerCase(c);
        if (lowerCaseChar == 'a') {
            return "+";
        } else if (lowerCaseChar == 'e') {
            return "-";
        } else if (lowerCaseChar == 'i') {
            return "*";
        } else if (lowerCaseChar == 'o') {
            return "/";
        } else {
            return "+"; // Default case
        }
    }

    private String buildUnaryFunction(char c) {
        int index = (c - 'A') % UNARY_FUNCTIONS.length;
        return UNARY_FUNCTIONS[index] + "(x)";
    }

    private String buildVariable(char c) {
        int index = (c - 'a') % VARIABLES.length;
        return VARIABLES[index];
    }


    /**
     * Combines operands and operators into a valid subexpression.
     *
     * @param operators The stack of operators.
     * @param operands  The stack of operands.
     * @return The combined expression as a string.
     */
    private String combineExpression(Stack<String> operators, Stack<String> operands) {
        String operator = operators.pop();
        String operand2 = operands.pop();
        String operand1 = operands.pop();
        return "(" + operand1 + " " + operator + " " + operand2 + ")";
    }

    protected double imageToDomainScale(int value, int bounds) {
        final double DOMAIN_MIN = -1;
        final double DOMAIN_MAX = 1;
        double range = DOMAIN_MAX - DOMAIN_MIN;
        return ((double) value / bounds) * range + DOMAIN_MIN;
    }


    /**
     * Creates an expression tree from a string function.
     *
     * @param function The function as a string.
     * @return The ExpressionTreeNode representing the function.
     */
    ExpressionTreeNode createExpression(String function) {
        ExpressionTreeGenerator expTreeGen = new ExpressionTreeGenerator();
        return expTreeGen.makeExpression(function);
    } // this will be handled in the evaluator
}



