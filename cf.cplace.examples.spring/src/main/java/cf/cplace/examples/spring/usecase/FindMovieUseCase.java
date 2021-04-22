package cf.cplace.examples.spring.usecase;

import java.util.Collection;

import cf.cplace.examples.spring.domain.model.Movie;

public interface FindMovieUseCase {

    Movie findById(String id);

    Collection<Movie> findByName(String name);

    Collection<Movie> findAll();
}
