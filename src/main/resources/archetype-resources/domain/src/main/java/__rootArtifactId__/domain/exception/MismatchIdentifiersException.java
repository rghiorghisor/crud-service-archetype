#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.domain.exception;

/**
 * Signals that two ids do not match. Usually this type of exception occurs when a call is made for an id but the
 * passed resource has a different id.
 */
public class MismatchIdentifiersException extends ApplicationException {

	/**
	 * The serial version UID associated with this class.
	 */
	private static final long serialVersionUID = 2177228208787702285L;
	
	public static final String MESSAGE = "Identifiers are not matching";
	
	public static final int CODE = 40001;
	
	public MismatchIdentifiersException(Object oneId, Object secondId) {
		super(CODE, MESSAGE);
		
		additionalDetails.put("id", new Object[] {oneId, secondId});		
	}
}