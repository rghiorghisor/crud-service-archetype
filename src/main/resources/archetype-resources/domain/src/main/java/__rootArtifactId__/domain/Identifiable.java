#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.domain;

public interface Identifiable<T> {

    public T getId();

    public void setId(T id); 
}
