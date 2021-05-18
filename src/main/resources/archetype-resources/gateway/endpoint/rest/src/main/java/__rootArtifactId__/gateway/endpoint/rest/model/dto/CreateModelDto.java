#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateModelDto {

	private String name;

}
