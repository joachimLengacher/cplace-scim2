package cf.cplace.examples.spring.usecase.impl;

import java.util.Collection;

import cf.cplace.examples.spring.usecase.DirectorUseCase;
import com.google.common.base.Preconditions;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.usecase.CreateMovieUseCase;
import cf.cplace.examples.spring.usecase.FindMovieUseCase;

public class MovieApplication implements FindMovieUseCase, CreateMovieUseCase, DirectorUseCase {

    private final MovieRepository movieRepository;

    public MovieApplication(MovieRepository movieRepository) {
        this.movieRepository = Preconditions.checkNotNull(movieRepository);
    }

    @Override
    public Movie findById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Collection<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public Collection<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public String create(String name) {
        return movieRepository.create(name);
    }

    @Override
    public void addDirectorToMovie(String actorId, String movieId) {
        // TODO:
    }
}
