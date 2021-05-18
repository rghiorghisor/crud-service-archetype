#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.domain.model;

import ${package}.${rootArtifactId}.domain.Identifiable;

public class Model implements Identifiable<String> {

	private String id;

	public Model() {

	}

	public Model(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
}
