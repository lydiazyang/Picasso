package picasso.parser.language.operators;

import picasso.parser.language.ExpressionTreeNode;
import picasso.parser.language.expressions.RGBColor;
import picasso.parser.language.expressions.Variable;
import picasso.parser.tokens.Token;
import picasso.parser.tokens.operations.AssignmentToken;
import picasso.parser.IdentifierAnalyzer;

/**
 * Represents the Assignment operation in the Picasso language.
 * 
 * @author Aiden Boeshans
 * 
 */
public class Assignment extends BinaryOperator {
	
	private Variable variable;

	/**
	 * Creates an assignment expression with the given variable and right hand expression
	 * 
	 * @param variable the given variable
	 * @param rhsExpression the right hand side expression
	 */
    public Assignment(Variable variable, ExpressionTreeNode rhsExpression) {
        super(variable, rhsExpression);
        this.variable = variable;
    }

	/**
	 * 
	 * 
	 * @return the color from evaluating the addition of x and y
	 */
	@Override
    public RGBColor evaluate(double x, double y) {
        //Evaluate the right hand side expression and store result in variable assigned to it
		//RGBColor result = this.right.evaluate(x,y);
		//Store the result in the assignment symbol table
		//get just the variable from the name
		String ass = variable.getName();
		int col = ass.indexOf(":");
		String name = ass.substring(col+2,ass.length());
		IdentifierAnalyzer.storeAssignmentResult(name,this.right);
		//Return the result
		return new RGBColor(1, 1, 1);
		
    }
    
}
