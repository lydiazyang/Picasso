package picasso.view.commands;

import java.awt.Color;
import java.awt.Dimension;
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
    private Evaluator evaluator;
    private static final String[] VARIABLES = {"x", "y"};
    private static final String[] UNARY_FUNCTIONS = {"sin", "cos", "tan", "atan", "exp", "ceil", "floor", "wrap", "abs", "clamp"};

    /**
     * Constructor for the StringEvaluator class
     *
     * @param input  JTextField from which the input string is obtained.
     * @param evaluator The evaluator used to process the generated expression.
     */
    public StringEvaluator(JTextField input, Evaluator evaluator) {
        this.input = input;
        this.evaluator = evaluator; 
    }

    /**
     * Executes the string evaluation process for a given Pixmap target.
     * Generates an expression from the input string and uses the evaluator to apply
     * this expression to each pixel in the image.
     * 
     * @param target The Pixmap object representing image to be processed.
     */
    @Override
    public void execute(Pixmap target) {
        String inputString = input.getText();
        String generatedExpression = generateExpressionFromString(inputString);
        evaluator.execute(target, generatedExpression);
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
                } else if (Character.isLowerCase(c)) {
                    operands.push(buildVariable(c));
                }
                // Ignore characters that do not match any of the above categories

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


    /**
     * Builds unary function expression based on uppercase character. 
     * Each letter corresponds to a unary function.
     * @param c The uppercase character
     * @return Unary function as a string.
     */
    private String buildUnaryFunction(char c) {
        int index = (c - 'A') % UNARY_FUNCTIONS.length;
        return UNARY_FUNCTIONS[index] + "(x)";
    }

    /**
     * Builds variable based on lowercase character. Each letter corresponds to a variable.
     * @param c Lowercase character
     * @return Corresponding variable as string
     */
    private String buildVariable(char c) {
        if (Character.isLowerCase(c)) {
            int index = Math.abs(c - 'a') % VARIABLES.length;
            return VARIABLES[index];
        }
        return "x"; // Default variable if not a lowercase letter
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

}



