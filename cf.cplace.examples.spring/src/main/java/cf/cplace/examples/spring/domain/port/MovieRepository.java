package cf.cplace.examples.spring.domain.port;

import cf.cplace.examples.spring.domain.model.Movie;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;

/**
 * Handles persistence for {@link Movie} instances.
 */
@ParametersAreNonnullByDefault
public interface MovieRepository {

    /**
     * Create a new {@link Movie} instance.
     * @param name the movie's name
     * @return the new instance' unique ID
     * @throws ForbiddenException if creating the instance is not allowed for the current user
     */
    @Nonnull
    String create(String name);

    /**
     * Updates an existing movie.
     * @param movie the movie instance to update.
     * @throws ForbiddenException if updating the instance is not allowed for the current user
     */
    void save(Movie movie);

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
