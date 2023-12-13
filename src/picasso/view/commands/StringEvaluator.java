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
    * Generates a basic expression from the input string.
    *
    * @param input The input string.
    * @return The basic generated expression.
    */
    private String generateExpressionFromString(String input) {
        Stack<String> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        Random random = new Random();


        for (char c : input.toCharArray()) {
            if (isOperatorChar(c)) {
                operators.push(buildOperator(c));
            } else {
                operands.push(buildOperand(c, random));
            }


            if (operands.size() >= 2 && !operators.isEmpty()) {
                String combinedExpr = combineExpression(operators, operands);
                operands.push(combinedExpr);
            }
        }


        while (!operators.isEmpty() && operands.size() >= 2) {
            String combinedExpr = combineExpression(operators, operands);
            operands.push(combinedExpr);
        }


        while (operands.size() > 1) {
            String operand2 = operands.pop();
            String operand1 = operands.pop();
            String combinedExpr = "(" + operand1 + " + " + operand2 + ")";
            operands.push(combinedExpr);
        }


        if (operands.isEmpty()) {
            return "0";
        } else {
            return operands.pop();
        }

    }

    /**
     * Determines if a character should be treated as an operator if it is a vowel.
     *
     * @param c The character to check.
     * @return True if the character is an operator, false otherwise.
     */
    private boolean isOperatorChar(char c) {
        return "aeiou".indexOf(Character.toLowerCase(c)) >= 0;
    }


    /**
    * Builds an operand based on a character.
    *
    * @param c The character.
    * @return The operand as a string.
    */
    private String buildOperand(char c) {
        if (Character.isDigit(c)) {
            return String.valueOf(c);
        } else {
            if (random.nextDouble() < 0.5) { 
                return buildRandomColor();
            } else {
                return VARIABLES[random.nextInt(VARIABLES.length)];
            }
        }
    }

    /**
    * Builds an operator based on a character.
    *
    * @param c The character.
    * @return The operator as a string.
    */
    private String buildOperator(char c) {
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
     * Generates a random RGB color expression.
     *
     * @return The color expression as a string.
     */
    private String buildRandomColor() {
        Random random = new Random();
        double red = random.nextDouble() * 2 - 1;
        double green = random.nextDouble() * 2 - 1;
        double blue = random.nextDouble() * 2 - 1;
        return "[" + red + "," + green + "," + blue + "]";
    }

}
