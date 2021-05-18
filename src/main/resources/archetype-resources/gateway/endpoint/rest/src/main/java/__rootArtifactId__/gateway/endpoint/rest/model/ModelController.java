#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${rootArtifactId}.gateway.endpoint.rest.model;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.${rootArtifactId}.domain.model.Model;
import ${package}.${rootArtifactId}.gateway.endpoint.rest.model.dto.ModelDto;
import ${package}.${rootArtifactId}.gateway.endpoint.rest.model.dto.CreateModelDto;
import ${package}.${rootArtifactId}.service.ModelService;
import ${package}.${rootArtifactId}.gateway.endpoint.rest.BaseController;

import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping(value = ModelController.BASE_MAPPING)
@Validated
public class ModelController extends BaseController {

	public static final String BASE_MAPPING = BaseController.API_MAPPING + "/models";

	@Autowired
	private ModelService modelService;

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody CreateModelDto template) throws Exception {
		// Convert input.
		Model templateModel = convertParameter(template, Model.class);

		// Perform action.
		Model newModel = modelService.create(templateModel);

		return createLocationHeader(newModel);
	}

	@GetMapping
	public Collection<Model> readAll() {
		Collection<Model> models = modelService.readAll();

		return models;
	}

	@GetMapping("{id}")
	public Model readOne(@PathVariable String id) {
		return modelService.read(id);
	}

	@PutMapping("{id}")
	public Model update(@PathVariable String id, @RequestBody Model template) {
		return modelService.update(template);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> update(@PathVariable String id) throws Exception {
		modelService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@PatchMapping(path = "{id}", consumes = "application/json-patch+json")
	public ResponseEntity<ModelDto> patchFile(@PathVariable String id, @RequestBody JsonPatch patch) throws Exception {
		Model model = modelService.read(id);
		
		Model modelPatched = applyPatch(patch, model);
		modelPatched = modelService.update(modelPatched);
		
		ModelDto toReturn = convertParameter(modelPatched, ModelDto.class);
		
		return ResponseEntity.ok(toReturn);
	}
}
