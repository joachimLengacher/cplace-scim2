package cf.cplace.examples.spring.adapter.rest;

import java.time.LocalDate;

public final class DirectorRepresentation {

    private final String id;
    private final String name;
    public final LocalDate birthday; // TODO: show how to format

    public DirectorRepresentation(String id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
