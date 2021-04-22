package cf.cplace.examples.spring.domain.model;

import com.google.common.base.Preconditions;

public class Movie {
    private final String id;
    private String name;

    public Movie(String id, String name) {
        this.id = Preconditions.checkNotNull(id);
        this.name = Preconditions.checkNotNull(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
