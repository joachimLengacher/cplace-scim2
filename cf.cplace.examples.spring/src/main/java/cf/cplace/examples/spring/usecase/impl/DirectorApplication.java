package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.domain.port.Messaging;
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
    private final Messaging messaging;

    public DirectorApplication(DirectorRepository directorRepository, Messaging messaging) {
        this.directorRepository = Preconditions.checkNotNull(directorRepository);
        this.messaging = Preconditions.checkNotNull(messaging);
    }

    @Override
    @Nonnull
    public String create(String name, LocalDate birthday) {
        Preconditions.checkNotNull(name, "Name is required");
        final String directorId = directorRepository.create(name, birthday);
        messaging.directorCreated(directorRepository.findById(directorId));
        return directorId;
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
