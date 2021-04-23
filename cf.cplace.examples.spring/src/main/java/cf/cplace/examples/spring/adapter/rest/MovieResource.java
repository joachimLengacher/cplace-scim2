package cf.cplace.examples.spring.adapter.rest;


import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.usecase.CreateMovieUseCase;
import cf.cplace.examples.spring.usecase.FindMovieUseCase;
import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;

@RestController
@CplaceRequestMapping(path = "/cf.cplace.examples.spring")
public class MovieResource {

    private final FindMovieUseCase findMovieUseCase;
    private final CreateMovieUseCase createMovieUseCase;

    public MovieResource(FindMovieUseCase findMovieUseCase, CreateMovieUseCase createMovieUseCase) {
        this.findMovieUseCase = Preconditions.checkNotNull(findMovieUseCase);
        this.createMovieUseCase = Preconditions.checkNotNull(createMovieUseCase);
    }

    @GetMapping(value = {"/movie/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MovieRepresentation findById(@PathVariable("id") String id) {
        return toMovieRepresentation(findMovieUseCase.findById(id));
    }

    @GetMapping(value = {"/movie"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<MovieRepresentation> find(@RequestParam(required = false) String name) {
        if (name != null) {
            return toMovieRepresentations(findMovieUseCase.findByName(name));
        }
        return toMovieRepresentations(findMovieUseCase.findAll());
    }

    @PostMapping(value = {"/movie"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody CreateMovieRequest createMovieRequest) {
        return createMovieUseCase.create(createMovieRequest.getName());
    }

    private Collection<MovieRepresentation> toMovieRepresentations(Collection<Movie> movies) {
        return movies.stream().map(this::toMovieRepresentation).collect(Collectors.toList());
    }

    private MovieRepresentation toMovieRepresentation(Movie movie) {
        return new MovieRepresentation(movie.getId(), movie.getName());
    }

}
