/**
 * 
 */
package picasso.parser.tokens;

import java.io.File;
import java.nio.file.Path;

/**
 * Represents an image file (using strings).
 * An ImageToken is immutable: once created it doesn't change.
 * 
 * @author Han Huynh
 */
public class ImageToken extends Token {

	private final String fileName;
	private final String filePath;
	

	/**
	 * Constructs a token representing the value
	 * 
	 * @param file name of the image found under the images folder
	 */
	public ImageToken(String fileName) {
		super("Image Token");
		this.filePath = Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + fileName;
		boolean error = false;
		String errorMsg = "";
		if (!new File(filePath).canRead()) {
			error = true;
			errorMsg += "File cannot be found. ";
		}

		if (error) {
			throw new IllegalArgumentException(errorMsg);
		}

		this.fileName = fileName;
	}

	/**
	 * Returns the image's file name
	 * @return the image's file name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * Returns the image's file path
	 * @return the image's file path
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * @return true iff o is an ImageToken with same value
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ImageToken)) {
			return false;
		}
		ImageToken other = (ImageToken) o;
		return this.fileName.equals(other.fileName) && this.filePath.equals(other.filePath);
	}

	public String toString() {
		return super.toString() + ": " + fileName;
	}

	/**
	 * 
	 * @return true if file is found in the images folder
	 */
	public static boolean isValidFile(String fileName) {
		String tempPath = Path.of("").toAbsolutePath().toString() + File.separator + "images" + File.separator + fileName;
		return new File(tempPath).canRead();
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public boolean isFunction() {
		return false;
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
