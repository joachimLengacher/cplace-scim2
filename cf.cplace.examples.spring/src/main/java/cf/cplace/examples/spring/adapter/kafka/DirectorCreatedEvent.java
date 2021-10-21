package cf.cplace.examples.spring.adapter.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectorCreatedEvent {

    private final String id;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DirectorCreatedEvent(@JsonProperty("id") String id, @JsonProperty("name") String name) {
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
