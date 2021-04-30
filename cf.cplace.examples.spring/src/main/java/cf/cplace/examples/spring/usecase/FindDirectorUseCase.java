package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Director;

import java.util.Collection;

/**
 * A collection of scenarios to find movie directors. They make up the "Find director" use case.
 */
public interface FindDirectorUseCase {

    /**
     * Finds a {@link Director} by its ID.
     * @param id the director's unique ID.
     * @return the director
     */
    Director findById(String id);

    /**
     * Finds all {@link Director} instances.
     * @return a collection of all directors. May be an empty collection.
     */
    Collection<Director> findAll();
}
