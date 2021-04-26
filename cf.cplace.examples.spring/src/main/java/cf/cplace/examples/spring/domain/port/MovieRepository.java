package cf.cplace.examples.spring.domain.port;

import java.util.Collection;

import cf.cplace.examples.spring.domain.model.Movie;

public interface MovieRepository {

    String create(String name);

    void save(Movie movie);

    Movie findById(String id);

    Collection<Movie> findByName(String name);

    Collection<Movie> findAll();
}
