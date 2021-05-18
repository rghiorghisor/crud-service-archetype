#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest;

import java.net.URI;

import ${package}.${rootArtifactId}.domain.Identifiable;
import ${package}.${rootArtifactId}.gateway.endpoint.rest.dto.DtoMapper;
import ${package}.${rootArtifactId}.domain.exception.ApplicationException;
import ${package}.${rootArtifactId}.domain.exception.MismatchIdentifiersException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

/**
 * Base controller that defines and implements the basic functionality of any REST controller.
 */
public abstract class BaseController {

	/**
	 * Default base mapping for API requests.
	 */
	public static final String API_MAPPING = "/api/v1";

   	@Autowired
	private DtoMapper dtoMapper;

	/**
   	 * Custom JSON object mapper to be used for internal operations.
   	 */
   	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Converts the provided {@code source} object to an instance of the {@code destinationType}. This conversion must
	 * be considered a basic input operation, i.e. the conversion of input parameters to some other objects, usually
	 * DTO to Model (or the other-way around). Thus in case of failure {@link IllegalArgumentException} will be thrown.
	 *
	 * <p>Must not be used as a default conversion utility method.</p>
	 *
	 * @param <T> The destination type.
	 * @param source The source object that must be converted.
	 * @param destinationType The type of the instance that must obtained.
	 * @return A newly created {@code <T>} instance.
	 * @throws IllegalArgumentException In case something goes wrong, the source and destination are considered
	 * incompatible, thus invalid input is assumed.
	 */
	protected <T> T convertParameter(Object source, Class<T> destinationType) throws IllegalArgumentException {
		return dtoMapper.convertParameter(source, destinationType);
	}

	/**
	 * Creates a new empty response, with the location header set to the location of the provided request. This method
	 * must be called after the {@code identifiable} resource was created, to obtain the REST POST response associated 
	 * with the creation action.
	 * 
	 * @param <T> The type of identifier for the newly created entity.
	 * @param identifiable The entity that was just created and whose location must be compiled.
	 * @return A new empty response with the proper location set.
	 */
	protected <T> ResponseEntity<Void> createLocationHeader(Identifiable<T> identifiable) {
		T createdResourceId = identifiable.getId();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdResourceId)
				.toUri();

		return ResponseEntity.created(location).build();		
	}

	/**
	 * Applies the give {@link JsonPatch} to the given object.
	 *  
	 * @param <T> The type of the entity that must be patched.
	 * @param patch The patch to be applied.
	 * @param target The target on which the patch must be applied.
	 * @return The newly patched instance.
	 * @throws JsonPatchException In case the patch is not well formed.
	 * @throws JsonProcessingException In case the patch cannot be applied on the given instance.
	 */
	@SuppressWarnings("unchecked")
	protected <T> T applyPatch(JsonPatch patch, T target) throws JsonPatchException, JsonProcessingException{
		JsonNode toPatch = patch.apply(objectMapper.convertValue(target, JsonNode.class));
		Class<? extends T> targetClass = (Class<? extends T>) target.getClass();
				
		return objectMapper.treeToValue(toPatch, targetClass);
	}

	/**
	 * Checks if the two passed identifiers match. By matching it is meant that they must have values (non-null) and 
	 * their values must be equal. 
	 * 
	 * @param <T> The identifier type.
	 * @param id The first identifier.
	 * @param identifiable The identifiable instance that must match the identifier.
	 * @throws ApplicationException In case the two identifier do not match, or if something goes wrong.
	 */
	protected <T> void checkIdMismatch(T id, Identifiable<T> identifiable) throws ApplicationException {
		T entityId = identifiable.getId();
		if (checkIdMismatch(id, entityId)) {
			return;
		}
		
		throw new MismatchIdentifiersException(id, entityId);
	}
	
	/**
	 * Checks if the two passed identifiers match. By matching it is meant that they must have values (non-null) and 
	 * their values must be equal.
	 * 
	 * @param <T> The identifier type.
	 * @param firstId The first of the identifiers.
	 * @param secondId The second of the identifiers.
	 * @return {@code true} if the ids match and are not null, {@code false} otherwise.
	 */
	private <T> boolean checkIdMismatch(T firstId, T secondId) {
		if (firstId == null | secondId == null) {
			return false;
		}
		
		return firstId.equals(secondId);
	}
	
}