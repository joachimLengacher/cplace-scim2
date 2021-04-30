package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Director;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;

/**
 * A collection of scenarios to create movie directors. They make up the "Create director" use case.
 */
@ParametersAreNonnullByDefault
public interface CreateDirectorUseCase {

    /**
     * Create a new {@link Director} instance.
     * @param name the director's name
     * @param birthday the director's birthday
     * @return the new instance' unique ID
     */
    @Nonnull
    String create(String name, LocalDate birthday);
}
