package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.usecase.AssignDirectorUseCase;
import cf.cplace.examples.spring.usecase.CreateMovieUseCase;
import cf.cplace.examples.spring.usecase.FindMovieUseCase;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class MovieApplication implements FindMovieUseCase, CreateMovieUseCase, AssignDirectorUseCase {

    private final MovieRepository movieRepository;

    public MovieApplication(MovieRepository movieRepository) {
        this.movieRepository = Preconditions.checkNotNull(movieRepository);
    }

    @Override
    @Nonnull
    public Movie findById(String id) {
        Preconditions.checkNotNull(id, "ID is required");
        return movieRepository.findById(id);
    }

    @Override
    @Nonnull
    public Collection<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    @Nonnull
    public Collection<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    @Nonnull
    public String create(String name) {
        Preconditions.checkNotNull(name, "Name is required");
        return movieRepository.create(name);
    }

    @Override
    public void addDirectorToMovie(String directorId, String movieId) {
        Movie movie = movieRepository.findById(movieId);
        movie.setDirectorId(directorId);
        movieRepository.save(movie);
    }
}
