package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Movie;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A collection of scenarios to create movies. They make up the "Create movie" use case.
 */
@ParametersAreNonnullByDefault
public interface CreateMovieUseCase {

    /**
     * Create a new {@link Movie} instance.
     * @param name the movie's name
     * @return the new instance' unique ID
     */
    @Nonnull
    String create(String name);
}
