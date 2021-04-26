package cf.cplace.examples.spring.usecase;

import cf.cplace.examples.spring.domain.model.Director;

import java.util.Collection;

public interface FindDirectorUseCase {

    Director findById(String id);

    Collection<Director> findAll();
}
