package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.NotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

/**
 * A collection of scenarios to find movies. They make up the "Find movie" use case.
 */
@ParametersAreNonnullByDefault
public interface FindMovieUseCase {

    /**
     * Finds a {@link Movie} by its ID.
     * @param id the movie's unique ID.
     * @return the movie
     * @throws NotFoundException if an entity with that ID doesn't exist
     * @throws ForbiddenException if reading the instance is not allowed for the current user
     */
    @Nonnull
    Movie findById(String id);

    /**
     * Finds {@link Movie}'s by their name.
     * @param name the movie's name
     * @return a collection of movies with the same name. May be an empty collection
     * @throws ForbiddenException if reading the instances is not allowed for the current user
     */
    @Nonnull
    Collection<Movie> findByName(String name);

    /**
     * Finds all {@link Movie} instances.
     * @return a collection of all movies. May be an empty collection.
     * @throws ForbiddenException if reading the instances is not allowed for the current user
     */
    @Nonnull
    Collection<Movie> findAll();
}
