package cf.cplace.examples.spring.adapter.rest;

/**
 * The JSON representation of a movie.
 */
public final class MovieRepresentation {

    private final String id;
    private final String name;
    private final String directorId;

    public MovieRepresentation(String id, String name, String directorId) {
        this.id = id;
        this.name = name;
        this.directorId = directorId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirectorId() {
        return directorId;
    }
}
