#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest.dto;

import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Mapper designated with (and only with) the conversion between Data Transfer Objects and Domain Model objects.
 */
@Component
@Scope("singleton")
public class DtoMapper {
	
	/**
	 * Logger instance associated with this class.
	 */
	private final Logger logger = LoggerFactory.getLogger(DtoMapper.class);
	
	/**
	 * Instance to be used for the actual object to object conversion.
	 */
	private final ModelMapper modelMapper;
	
	/**
	 * Default constructor.
	 */
	public DtoMapper() {
		modelMapper = new ModelMapper();
	}
	
	/**
	 * Converts the provided {@code source} object to an instance of the {@code destinationType}. This conversion must
	 * be considered a basic input operation, i.e. the conversion of input parameters to some other objects, usually
	 * DTO to Model (or the other-way around). Thus in case of failure {@link IllegalArgumentException} will be thrown.
	 *
	 * @param <T> The destination type.
	 * @param source The source object that must be converted.
	 * @param destinationType The type of the instance that must obtained.
	 * @return A newly created {@code <T>} instance.
	 * @throws IllegalArgumentException In case something goes wrong, the source and destination are considered
	 * incompatible, thus invalid input is assumed.
	 */
	public <T> T convertParameter(Object source, Class<T> destinationType) throws IllegalArgumentException {
		try {
			return modelMapper.map(source, destinationType);
		} catch (MappingException | ConfigurationException e) {
			logConvertParameterException(source, destinationType, e);

			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			logConvertParameterException(source, destinationType, e);

			throw e;
		}
	}
	
	/**
	 * Logs the specified conversion exception. This is considered an INFO message as input validation is a <i>normal</i>
	 * application behavior.
	 *
	 * @param source The source object that caused the conversion exception.
	 * @param destinationType The type of the instance that could not be obtained.
	 * @param e The actual exception happening.
	 */
	private void logConvertParameterException(Object source, Class<?> destinationType, Exception e) {
		Class<?> sourceClass = null;
		if (source != null) {
			sourceClass = source.getClass();
		}

		// This is considered an INFO message as input validation is a "normal" application behavior.
		logger.info("Invalid {} -> {} conversion.", sourceClass, destinationType, e);
		logger.debug("Cannot perform conversion {} -> {}.", source, destinationType, e);
	}
	
}