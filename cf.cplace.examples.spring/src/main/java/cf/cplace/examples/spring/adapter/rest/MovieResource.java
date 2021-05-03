package cf.cplace.examples.spring.adapter.rest;


import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.usecase.AssignDirectorUseCase;
import cf.cplace.examples.spring.usecase.CreateMovieUseCase;
import cf.cplace.examples.spring.usecase.FindMovieUseCase;
import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import com.google.common.base.Preconditions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A resource to manage movies.
 */
@RestController
@CplaceRequestMapping(path = "/cf.cplace.examples.spring/movie")
public class MovieResource {

    private final FindMovieUseCase findMovieUseCase;
    private final CreateMovieUseCase createMovieUseCase;
    private final AssignDirectorUseCase assignDirectorUseCase;

    public MovieResource(
            FindMovieUseCase findMovieUseCase,
            CreateMovieUseCase createMovieUseCase,
            AssignDirectorUseCase assignDirectorUseCase) {
        this.findMovieUseCase = Preconditions.checkNotNull(findMovieUseCase);
        this.createMovieUseCase = Preconditions.checkNotNull(createMovieUseCase);
        this.assignDirectorUseCase = Preconditions.checkNotNull(assignDirectorUseCase);
    }

    @GetMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MovieRepresentation findById(@PathVariable("id") String id) {
        return toMovieRepresentation(findMovieUseCase.findById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<MovieRepresentation> find(@RequestParam(required = false) String name) {
        if (name != null) {
            return toMovieRepresentations(findMovieUseCase.findByName(name));
        }
        return toMovieRepresentations(findMovieUseCase.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody CreateMovieRequest createMovieRequest) {
        return createMovieUseCase.create(createMovieRequest.getName());
    }

    @PostMapping(value = {"/{movieId}/director"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addDirector(@PathVariable("movieId") String movieId, @RequestBody AddDirectorRequest addDirectorRequest) {
        assignDirectorUseCase.addDirectorToMovie(addDirectorRequest.getDirectorId(), movieId);
    }

    private Collection<MovieRepresentation> toMovieRepresentations(Collection<Movie> movies) {
        return movies.stream().map(this::toMovieRepresentation).collect(Collectors.toList());
    }

    private MovieRepresentation toMovieRepresentation(Movie movie) {
        return new MovieRepresentation(movie.getId(), movie.getName(), movie.getDirectorId());
    }

}
