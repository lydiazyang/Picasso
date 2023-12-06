package picasso.view.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import picasso.model.Pixmap;
import picasso.util.FileCommand;
import picasso.view.Frame;

/**
 * Open the chosen image file and display in the Pixmap target.
 * 
 * @author Robert C Duvall
 */
public class Reader extends FileCommand<Pixmap> {
	private Frame frame;

	/**
	 * Creates a Reader object, which prompts users for image files to open
	 */
	public Reader() {
		super(JFileChooser.OPEN_DIALOG);
	}

	public Reader(Frame frame) {
		super(JFileChooser.OPEN_DIALOG);
		this.frame = frame;
	}

	/**
	 * Displays the image file on the given target.
	 */
	public void execute(Pixmap target) {
		String fileName = getFileName();
		if (fileName != null) {
			if (fileName.endsWith(".exp")){
				readExpressionFromFile(fileName);
			} else {
				target.read(fileName);
			}
		}
	}

	private void readExpressionFromFile(String filePath) {
		
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	// evaluate each line
            StringBuilder expressionBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                expressionBuilder.append(line);
            }

            String expression = expressionBuilder.toString();
            frame.setExpressionInTextField(expression);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
