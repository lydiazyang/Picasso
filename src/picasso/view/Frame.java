package picasso.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.ThreadedCommand;
import picasso.view.commands.*;

/**
 * Main container for the Picasso application
 *
 * @author Robert Duvall (rcd@cs.duke.edu)
 * 
 */
@SuppressWarnings("serial")
public class Frame extends JFrame {
	private JTextField functionTextField;
	
	public Frame(Dimension size) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// create GUI components
		Canvas canvas = new Canvas(this);
		canvas.setSize(size);

		// add commands to test here
		ButtonPanel commands = new ButtonPanel(canvas);
		functionTextField = new JTextField(20);
		Evaluator evaluator = new Evaluator(functionTextField);
		functionTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	evaluator.execute(canvas.getPixmap());
            	canvas.refresh();
            }});
		commands.add(new JLabel("Enter Function: "));
        commands.add(functionTextField);
        commands.add("Evaluate", new ThreadedCommand<Pixmap>(canvas, evaluator));
        commands.add("Random", new ThreadedCommand<Pixmap>(canvas, new RandomEvaluator()));
        commands.add("Generate from String", new ThreadedCommand<Pixmap>(canvas, new StringEvaluator()));
		commands.add("Open", new Reader(this, functionTextField));
		commands.add("Save", new Writer());
		

		// add our container to Frame and show it
		getContentPane().add(canvas, BorderLayout.CENTER);
		getContentPane().add(commands, BorderLayout.NORTH);
		pack();
	}

	public void setExpressionInTextField(String expression) {
        	functionTextField.setText(expression);
    	}
}
