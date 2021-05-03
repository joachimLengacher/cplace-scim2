package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.usecase.CreateDirectorUseCase;
import cf.cplace.examples.spring.usecase.FindDirectorUseCase;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.util.Collection;

@ParametersAreNonnullByDefault
public class DirectorApplication implements CreateDirectorUseCase, FindDirectorUseCase {

    private final DirectorRepository directorRepository;

    public DirectorApplication(DirectorRepository directorRepository) {
        this.directorRepository = Preconditions.checkNotNull(directorRepository);
    }

    @Override
    @Nonnull
    public String create(String name, LocalDate birthday) {
        Preconditions.checkNotNull(name, "Name is required");
        return directorRepository.create(name, birthday);
    }

    @Override
    @Nonnull
    public Director findById(String id) {
        Preconditions.checkNotNull(id, "ID is required");
        return directorRepository.findById(id);
    }

    @Override
    @Nonnull
    public Collection<Director> findAll() {
        return directorRepository.findAll();
    }
}
