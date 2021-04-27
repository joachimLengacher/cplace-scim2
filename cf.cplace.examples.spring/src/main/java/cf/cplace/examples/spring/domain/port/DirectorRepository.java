package cf.cplace.examples.spring.domain.port;

import cf.cplace.examples.spring.domain.model.Director;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Handles persistence for {@link Director} instances.
 */
@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public interface DirectorRepository {

    /**
     * Create a new {@link Director} instance.
     * @param name the director's name
     * @param birthday the director's birthday
     * @return the new instance' unique ID
     * @throws ForbiddenException if creating the instance is not allowed for the current user
     */
    String create(String name, @Nullable LocalDate birthday);

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
