#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.service;

import java.util.Collection;

import ${package}.${rootArtifactId}.domain.model.Model;
import ${package}.${rootArtifactId}.domain.exception.DuplicateEntityException;
import ${package}.${rootArtifactId}.domain.exception.InvalidEntityException;
import ${package}.${rootArtifactId}.domain.exception.ApplicationException;

/**
 *
 */
public interface ModelService {

	public Model create(Model template) throws DuplicateEntityException, InvalidEntityException, ApplicationException;

	public Model read(String id);

	public Collection<Model> readAll();

	public Model update(Model template);

	public Model delete(String id);
}
