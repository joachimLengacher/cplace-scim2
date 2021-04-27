package cf.cplace.examples.spring.adapter.rest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * The JSON representation of a movie director.
 */
public final class DirectorRepresentation {

    private final String id;
    private final String name;
    public final LocalDate birthday;

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

    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate getBirthday() {
        return birthday;
    }
}
