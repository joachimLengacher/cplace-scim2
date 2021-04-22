package cf.cplace.examples.spring.adapter.rest;

public final class MovieRepresentation {

    private final String id;
    private final String name;

    public MovieRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
