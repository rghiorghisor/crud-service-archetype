#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.domain.exception;

public class EntityNotFoundException extends ApplicationException {

	/**
	 * The serial version UID associated with this class.
	 */
	private static final long serialVersionUID = -6350530903189714537L;
	
	public static final String MESSAGE = "Resource not found";
	
	public static final int CODE = 40404;
	
	public EntityNotFoundException(Object id) {
		super(CODE, MESSAGE);
		
		additionalDetails.put("id", id);		
	}
}
