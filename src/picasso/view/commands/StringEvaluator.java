package picasso.view.commands;


import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.util.Stack;
import javax.swing.JTextField;
import picasso.model.Pixmap;
import picasso.parser.ExpressionTreeGenerator;
import picasso.parser.language.ExpressionTreeNode;
import picasso.util.Command;


/**
 * Evaluate a string expression for each pixel in a image.
 * 
 * @author Lydia Yang
 */
public class StringEvaluator implements Command<Pixmap> {
    private JTextField input;
    private static final String[] VARIABLES = {"x", "y"};
    private static final Random random = new Random();

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
        if (isOperatorChar(c)) {
            operators.push(buildOperator(c));
        } else {
            operands.push(buildOperand(c, random));
        }

        if (operands.size() > 1 && !operators.isEmpty()) {
            String combinedExpr = combineExpression(operators, operands);
            operands.push(combinedExpr);
        }
    }

    // Handle remaining operators and operands
    while (!operators.isEmpty() && operands.size() > 1) {
        String combinedExpr = combineExpression(operators, operands);
        operands.push(combinedExpr);
    }

    // Add additional operators if needed
    while (operands.size() > 1) {
        operators.push("+"); // Default operator
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


    /**
     * Builds an operand based on a character. It can be either a variable or a color.
     *
     * @param c      The character.
     * @param random The Random object used to generate random values.
     * @return The operand as a string.
     */
    private String buildOperand(char c, Random random) {
        if (Character.isDigit(c)) {
            return String.valueOf(c);
        } else {
            if (random.nextDouble() < 0.6) { // 60% chance to generate a color
                return buildRandomColor();
            } else {
                return VARIABLES[random.nextInt(VARIABLES.length)];
            }
        }
    }


    /**
     * Generates a random RGB color expression.
     *
     * @return The color expression as a string.
     */
    private String buildRandomColor() {
        double red = random.nextDouble() * 2 - 1;
        double green = random.nextDouble() * 2 - 1;
        double blue = random.nextDouble() * 2 - 1;
        return "[" + red + "," + green + "," + blue + "]";
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
    }
}



