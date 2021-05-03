package cf.cplace.examples.spring.usecase;

/**
 * The "Assign director to movie" use case .
 */
public interface AssignDirectorUseCase {

    /**
     * Assigns a director to a movie.
     * @param directorId the director's unique ID
     * @param movieId the movie's unique ID
     */
    void addDirectorToMovie(String directorId, String movieId);
}
