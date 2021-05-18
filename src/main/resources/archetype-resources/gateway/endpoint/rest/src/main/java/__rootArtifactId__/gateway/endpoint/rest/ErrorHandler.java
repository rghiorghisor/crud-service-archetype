#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest;

import java.util.Map;

import ${package}.${rootArtifactId}.domain.exception.ApplicationException;
import ${package}.${rootArtifactId}.domain.exception.DuplicateEntityException;
import ${package}.${rootArtifactId}.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	protected ResponseEntity<CustomExceptionSchema> handleNotFoundException(ApplicationException ex) {
		return internalHandle(ex, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateEntityException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	protected ResponseEntity<CustomExceptionSchema> handleConflictException(ApplicationException ex) {
		return internalHandle(ex, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	protected ResponseEntity<CustomExceptionSchema> handleBadRequestException(ApplicationException ex) {
		return internalHandle(ex, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<CustomExceptionSchema> internalHandle(ApplicationException ex, HttpStatus httpStatus) {
		CustomExceptionSchema exceptionSchema = new CustomExceptionSchema(ex);
		
		ResponseEntity<CustomExceptionSchema> responseEntity = new ResponseEntity<>(exceptionSchema, httpStatus);
		
		return responseEntity;		
	}
	
	/**
	 * The overriding of this method makes sure that any {@link MethodArgumentNotValidException} exception thrown by 
	 * the default JSON validation will be handled properly. 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		BindingResult bindingResult = ex.getBindingResult();
		if (bindingResult == null || bindingResult.getAllErrors().isEmpty()) {
			/*
			 *  If not binding result is present or no errors are there, there is nothing much we can do here,
			 *  thus let the default method process the message.
			 */			
			return super.handleMethodArgumentNotValid(ex, headers, status, request);				
		}
		
		StringBuilder message = new StringBuilder();
		
		for (ObjectError error : bindingResult.getAllErrors()) {
			message.append("[").append(error.getObjectName()).append("] ").append(error.getDefaultMessage());			
		}
		CustomExceptionSchema exceptionSchema = new CustomExceptionSchema(message.toString());
		ResponseEntity<Object> responseEntity = new ResponseEntity<>(exceptionSchema, HttpStatus.NOT_FOUND);	
		
		return responseEntity;
	}

	@AllArgsConstructor
	@Getter	
	@JsonInclude(Include.NON_NULL)
	public static class CustomExceptionSchema {
		
		/**
		 * The message of the error. This text must contain the main hints why a certain request has failed,
		 * in human readable form.
		 * E.g. "Entity cannot be found", "Conflict has been detected". 
		 */
		private String message;
	    
		/**
		 * The error code associated with the error. This is different then the HTTP response code, as a single 
		 * HTTP response code might represent different reasons for failure.
		 * The error code is the machine representation of the {@link #message}.   
		 */
		private Integer errorCode;
		
		/**
		 * Further details that can identify the actual failure reason. For example, for a {@code 404 Not Found}
		 * response, the details might contain the id of the resource not found. E.g.:
		 * 
		 * <pre> 
		 * {
		 *     "message": "Entity cannot be found.",
		 *     "errorCode": 40404,
		 *     <b>"details": {
		 *         "id": "102427061777986230"
		 *     }</b>
		 * }
		 * </pre>
		 */
		private Map<String, Object> details;
		
		public CustomExceptionSchema(String message) {
			this(message, null, null);
		}
		
		public CustomExceptionSchema(ApplicationException applicationException) {
			this(applicationException.getCustomMessage(), 
					applicationException.getCode(), 
					applicationException.getAdditionalDetails());
		}
		
		public CustomExceptionSchema(Exception applicationException) {
			this(applicationException.getMessage(), null, null);
		}
	}
}



