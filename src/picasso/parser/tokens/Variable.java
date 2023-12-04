package picasso.parser.tokens;

/**
 * Represents a variable token in the picasso language
 * 
 * @author Aiden Boeshans
 */

public class Variable extends Token {
	
	private String name;
	
	public Variable(String name) {
		super(name);
		this.name = name;
	}
	
	public String getValue() {
		return name;
	}
	
	@Override
	public boolean isConstant() {
		return false;
	}
	
	public boolean isFunction() {
		return false;
	}

}
