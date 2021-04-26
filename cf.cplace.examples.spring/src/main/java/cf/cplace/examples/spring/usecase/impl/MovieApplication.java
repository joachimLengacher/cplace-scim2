package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.usecase.AssignDirectorUseCase;
import cf.cplace.examples.spring.usecase.CreateMovieUseCase;
import cf.cplace.examples.spring.usecase.FindMovieUseCase;
import com.google.common.base.Preconditions;

import java.util.Collection;

public class MovieApplication implements FindMovieUseCase, CreateMovieUseCase, AssignDirectorUseCase {

    private final MovieRepository movieRepository;
    public final DirectorRepository directorRepository;

    public MovieApplication(MovieRepository movieRepository, DirectorRepository directorRepository) {
        this.movieRepository = Preconditions.checkNotNull(movieRepository);
        this.directorRepository = Preconditions.checkNotNull(directorRepository);
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
