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
		hDropdown.addActionListener(e -> selection());
	}
	
	@Override
	public void execute(Pixmap pixmap) {
		
	}
	
	public void showHistoryDropdown() {
	    if (evaluator.getExpressionList().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No history available", "History", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        String[] expressions = evaluator.getExpressionList().toArray(new String[0]);

	        // Truncate expressions for display purposes
	        String[] truncatedExpressions = new String[expressions.length];
	        for (int i = 0; i < expressions.length; i++) {
	            truncatedExpressions[i] = truncateExpression(expressions[i]);
	        }

	        // Show the dropdown with truncated expressions
	        String selectedExpression = (String) JOptionPane.showInputDialog(
	                null,
	                "Choose an expression:",
	                "History",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                truncatedExpressions,
	                truncatedExpressions[0]);

	        if (selectedExpression != null) {
	            // Retrieve the full expression associated with the truncated one
	            String fullExpression = getFullExpression(selectedExpression, expressions, truncatedExpressions);

	            // Add the full expression to the dropdown
	            hDropdown.addItem(fullExpression);
	            hDropdown.setSelectedItem(fullExpression);

	            // Set the full expression in the evaluator
	            evaluator.setInputText(fullExpression);
	        }
	    }
	}

	private String truncateExpression(String expression) {
	    // Implement your truncation logic here
	    // Example: Truncate to a fixed length of 20 characters
	    int maxLength = 20;
	    return expression.length() <= maxLength ? expression : expression.substring(0, maxLength - 3) + "...";
	}

	private String getFullExpression(String truncatedExpression, String[] expressions, String[] truncatedExpressions) {
	    // Find the full expression corresponding to the truncated expression
	    for (int i = 0; i < truncatedExpressions.length; i++) {
	        if (truncatedExpression.equals(truncatedExpressions[i])) {
	            return expressions[i];
	        }
	    }
	    return truncatedExpression; // Return the original expression if not found (optional)
	}

	
	private void selection() {
		String selectedExpression = (String) hDropdown.getSelectedItem();
		if (selectedExpression != null) {
			evaluator.setInputText(selectedExpression);
		}
	}
}