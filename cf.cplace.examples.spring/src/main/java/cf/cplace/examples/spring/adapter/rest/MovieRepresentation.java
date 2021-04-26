package cf.cplace.examples.spring.adapter.rest;

public final class MovieRepresentation {

    private String id;
    private String name;
    private String directorId;

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
