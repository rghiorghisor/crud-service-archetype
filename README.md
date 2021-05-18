# CRUD Service Maven Archetype (Java & Spring Boot)

Maven archetype for a simple Java based CRUD service, based on Spring Boot.

It creates a basic and fully functional service-boot-service, with Controller, Service and Domain layers.

## Usage

Download and install:
```console
git clone git clone https://github.com/rghiorghisor/crud-service-archetype

cd crud-service-archetype

mvn clean install
```

Generate service:
```console
mvn archetype:generate \
 -DserviceName={SERVICE_NAME} \
 -DartifactId={SERVICE_ARTIFACT_ID} \
 -DgroupId={SERVICE_GROUP_ID} \
 -Dversion={SERVICE_VERSION} \
 -DarchetypeGroupId=org.github.rghiorghisor.service \
 -DarchetypeArtifactId=crud-service-archetype \
 -DinteractiveMode=false
```
#### Example

Example usage for a service: `org.github.rghiorghisor.foo:1.0-SNAPSHOT`:
```console
mvn archetype:generate \
 -DserviceName=FOO \
 -DartifactId=foo \
 -DgroupId=org.github.rghiorghisor \
 -Dversion=1.0-SNAPSHOT \
 -DarchetypeGroupId=org.github.rghiorghisor.service \
 -DarchetypeArtifactId=crud-service-archetype \
 -DinteractiveMode=false
```
