package cf.cplace.examples.spring.domain.port;

import cf.cplace.examples.spring.domain.model.Director;

public interface Messaging {

    void directorCreated(Director director);
}
