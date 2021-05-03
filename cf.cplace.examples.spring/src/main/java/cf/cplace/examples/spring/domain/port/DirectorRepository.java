package cf.cplace.examples.spring.domain.port;

import cf.cplace.examples.spring.domain.model.Director;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Handles persistence for {@link Director} instances.
 */
@ParametersAreNonnullByDefault
public interface DirectorRepository {

    /**
     * Create a new {@link Director} instance.
     * @param name the director's name
     * @param birthday the director's birthday
     * @return the new instance' unique ID
     */
    @Nonnull
    String create(String name, @Nullable LocalDate birthday);

    /**
     * Finds a {@link Director} by its ID.
     * @param id the director's unique ID.
     * @return the director
     */
    @Nonnull
    Director findById(String id);

    /**
     * Finds all {@link Director} instances.
     * @return a collection of all directors. May be an empty collection.
     */
    @Nonnull
    Collection<Director> findAll();
}
