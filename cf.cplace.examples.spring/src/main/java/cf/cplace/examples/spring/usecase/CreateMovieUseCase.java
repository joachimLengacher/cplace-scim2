package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A collection of scenarios to create movies. They make up the "Create movie" use case.
 */
@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public interface CreateMovieUseCase {

    /**
     * Create a new {@link Movie} instance.
     * @param name the movie's name
     * @return the new instance' unique ID
     * @throws ForbiddenException if creating the instance is not allowed for the current user
     */
    String create(String name);
}
