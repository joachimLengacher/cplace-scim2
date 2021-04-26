package cf.cplace.examples.spring.domain.port;

import cf.cplace.examples.spring.domain.model.Director;

import java.time.LocalDate;
import java.util.Collection;

public interface DirectorRepository {

    String create(String name, LocalDate birthday);

    Director findById(String id);

    Collection<Director> findAll();
}
