#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import ${package}.${rootArtifactId}.domain.model.Model;
import ${package}.${rootArtifactId}.service.ModelService;

@Service
public class DefaultModelService implements ModelService {

    @Override
  	public Model create(Model project) {
		Model newModel = new Model("1");

		return newModel;
  	}

  	@Override
  	public Model read(String id) {
  		return null;
  	}

  	@Override
  	public Collection<Model> readAll() {
  		return null;
  	}

  	@Override
  	public Model update(Model template) {
  		return null;
  	}

  	@Override
  	public Model delete(String id) {
  		return null;
  	}
}
