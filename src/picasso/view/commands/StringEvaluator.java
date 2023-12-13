package picasso.view.commands;

import javax.swing.JTextField;
import picasso.model.Pixmap;
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
        String generatedExpression = generateBasicExpression(inputString);
    }

    /**
    * Generates a basic expression from the input string.
    *
    * @param input The input string.
    * @return The basic generated expression.
    */
    private String generateBasicExpression(String input) {
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            expression.append(buildOperand(input.charAt(i)));
            if (i < input.length() - 1) {
                expression.append(buildOperator(input.charAt(i)));
            }
        }
        return expression.toString();
    }

    /**
    * Builds an operand based on a character.
    *
    * @param c The character.
    * @return The operand as a string.
    */
    private String buildOperand(char c) {
        return "x"; // Placeholder 

    /**
    * Builds an operator based on a character.
    *
    * @param c The character.
    * @return The operator as a string.
    */
    private String buildOperator(char c) {
        return "+"; // Placeholder =
}
}
