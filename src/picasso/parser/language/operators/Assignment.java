package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.AssignmentToken;

/**
 * Represents the Assignment operation in the Picasso language.
 * 
 * @author Aiden Boeshans
 * 
 */
public class Assignment extends BinaryOperator {
	
	private String variable;

	/**
	 * Creates an assignment expression with the given variable and right hand expression
	 * 
	 * @param variable the given variable
	 * @param rhsExpression the right hand side expression
	 */
    public Assignment(String variable, ExpressionTreeNode rhsExpression, Token token) {
        super(null, rhsExpression, token);
        this.variable = variable;
    }



    /**
     * Gets the variable name
     * @return variable name
     */
    
    public String getVaribale() {
		return variable;
	}


	/**
	 * 
	 * 
	 * @return the color from evaluating the addition of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        //Evaluate the right hand side expression and store result in variable assigned to it
		RGBColor result = this.right.evaluate(x,y);
		//TODO: Store the result in the assignment symbol table
		
		//Return the result
		return result;
		
    }
    
}
