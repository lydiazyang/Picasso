package picasso.view.commands;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import picasso.model.Pixmap;
import picasso.util.Command;

/**
 * Save the chosen file.
 * 
 * @author Aiden Boeshans
 */
public class History implements Command<Pixmap> {
	private JComboBox<String> hDropdown;
	private Evaluator evaluator;
	
	public History(JComboBox<String> hDropdown, Evaluator evaluator) {
		this.hDropdown = hDropdown;
		this.evaluator = evaluator;
	}
	
	@Override
	public void execute(Pixmap pixmap) {
		
	}
	
	public void showHistoryDropdown() {
		if (evaluator.getExpressionList().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No history available", "History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] expressions = evaluator.getExpressionList().toArray(new String[0]);
            String selectedExpression = (String) JOptionPane.showInputDialog(
                    null,
                    "Choose an expression:",
                    "History",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    expressions,
                    expressions[0]);
            if (selectedExpression != null) {
            	hDropdown.addItem(selectedExpression);
            	hDropdown.setSelectedItem(selectedExpression);
            }
        }
	}	
}