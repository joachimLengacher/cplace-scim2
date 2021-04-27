package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.NotFoundException;

import java.util.Collection;

/**
 * A collection of scenarios to find movie directors. They make up the "Find director" use case.
 */
public interface FindDirectorUseCase {

    /**
     * Finds a {@link Director} by its ID.
     * @param id the director's unique ID.
     * @return the director
     * @throws NotFoundException if an entity with that ID doesn't exist
     * @throws ForbiddenException if reading the instance is not allowed for the current user
     */
    Director findById(String id);

    /**
     * Finds all {@link Director} instances.
     * @return a collection of all directors. May be an empty collection.
     * @throws ForbiddenException if reading the instances is not allowed for the current user
     */
    Collection<Director> findAll();
}
