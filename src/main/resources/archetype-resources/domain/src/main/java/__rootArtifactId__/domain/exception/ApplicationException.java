#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.domain.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

/**
 * An application exception signals that something went wrong during request handling and the failure reason is the 
 * client call. This is closely related with Client errors (HTTP 4** errors). 
 */
@Getter
public class ApplicationException extends Exception {

	/**
	 * The serial version UI associated with this class.
	 */
	private static final long serialVersionUID = 6118691710448097575L;

	/**
	 * The (internal) code associated with the message. This code provided better granularity than using the default
	 * HTTP response codes.
	 */
	private int code;
	
	/**
	 * The custom message associated with this exception.
	 */
	private String customMessage;
	
	/**
	 * Any additional error details that might debug the failure reasons.
	 */
	protected Map<String, Object> additionalDetails;

	/**
	 * Default constructor.
	 */
	public ApplicationException() {
		
	}
	
	/**
	 * Constructor.
	 *
	 * @param code The code associated with this exception.
	 * @param customMessage The message associated with this message (It is different than Exception#getMessage()).
	 */
	public ApplicationException(int code, String customMessage) {
		this.code = code;
		this.customMessage = customMessage;
		
		additionalDetails = new HashMap<>();			
	}	

}
