package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.usecase.CreateDirectorUseCase;
import cf.cplace.examples.spring.usecase.FindDirectorUseCase;
import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.util.Collection;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class DirectorApplication implements CreateDirectorUseCase, FindDirectorUseCase {

    private final DirectorRepository directorRepository;

    public DirectorApplication(DirectorRepository directorRepository) {
        this.directorRepository = Preconditions.checkNotNull(directorRepository);
    }

    @Override
    public String create(String name, LocalDate birthday) {
        return directorRepository.create(name, birthday);
    }

    @Override
    public Director findById(String id) {
        return directorRepository.findById(id);
    }

    @Override
    public Collection<Director> findAll() {
        return directorRepository.findAll();
    }
}
