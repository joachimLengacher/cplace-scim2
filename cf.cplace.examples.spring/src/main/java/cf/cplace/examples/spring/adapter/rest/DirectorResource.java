package cf.cplace.examples.spring.adapter.rest;


import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.usecase.CreateDirectorUseCase;
import cf.cplace.examples.spring.usecase.FindDirectorUseCase;
import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import com.google.common.base.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A resource to manage movie directors.
 */
@RestController
@CplaceRequestMapping(path = "/cf.cplace.examples.spring/director")
public class DirectorResource {

    private final FindDirectorUseCase findDirectorUseCase;
    private final CreateDirectorUseCase createDirectorUseCase;

    public DirectorResource(FindDirectorUseCase findDirectorUseCase, CreateDirectorUseCase createDirectorUseCase) {
        this.findDirectorUseCase = Preconditions.checkNotNull(findDirectorUseCase);
        this.createDirectorUseCase = Preconditions.checkNotNull(createDirectorUseCase);
    }

    @GetMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public DirectorRepresentation findById(@PathVariable("id") String id) {
        return toDirectorRepresentation(findDirectorUseCase.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<DirectorRepresentation> findAll() {
        return toDirectorRepresentations(findDirectorUseCase.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody CreateDirectorRequest createDirectorRequest) {
        return createDirectorUseCase.create(createDirectorRequest.getName(), createDirectorRequest.getBirthday());
    }

    private Collection<DirectorRepresentation> toDirectorRepresentations(Collection<Director> directors) {
        return directors.stream().map(this::toDirectorRepresentation).collect(Collectors.toList());
    }

    private DirectorRepresentation toDirectorRepresentation(Director director) {
        return new DirectorRepresentation(director.getId(), director.getName(), director.getBirthday());
    }

}