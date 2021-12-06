package cf.cplace.scim2.usecase;

import javax.annotation.Nonnull;

public interface DeleteUserUseCase {

    void deleteById(@Nonnull String id);
}
