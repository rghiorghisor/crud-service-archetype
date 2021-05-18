#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("${package}")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ${classNamePrefix}ServiceApplication {

	/**
	 * ${serviceName} Application main method.
	 *
	 * @param args Application arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(${classNamePrefix}ServiceApplication.class, args);
	}
}
