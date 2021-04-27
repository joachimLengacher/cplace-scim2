package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.NotFoundException;

/**
 * The "Assign director to movie" use case .
 */
public interface AssignDirectorUseCase {

    /**
     * Assigns a director to a movie.
     * @param directorId the director's unique ID
     * @param movieId the movie's unique ID
     * @throws NotFoundException if either the movie or the director doesn't exist
     * @throws ForbiddenException if updating the movie instance is not allowed for the current user
     */
    void addDirectorToMovie(String directorId, String movieId);
}
