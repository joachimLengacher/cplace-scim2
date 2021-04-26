package cf.cplace.examples.spring.usecase;

import java.time.LocalDate;

public interface CreateDirectorUseCase {
    String create(String name, LocalDate birthday);
}
