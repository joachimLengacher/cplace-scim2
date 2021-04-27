package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;

/**
 * A collection of scenarios to create movie directors. They make up the "Create director" use case.
 */
@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public interface CreateDirectorUseCase {

    /**
     * Create a new {@link Director} instance.
     * @param name the director's name
     * @param birthday the director's birthday
     * @return the new instance' unique ID
     * @throws ForbiddenException if creating the instance is not allowed for the current user
     */
    String create(String name, LocalDate birthday);
}
