package cf.cplace.examples.spring.adapter.rest;

import javax.validation.constraints.NotNull;

public final class CreateMovieRequest {
    private String name;

    @NotNull(message = "name is required")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
