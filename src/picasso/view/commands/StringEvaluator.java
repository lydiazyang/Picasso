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
        
    }
}
