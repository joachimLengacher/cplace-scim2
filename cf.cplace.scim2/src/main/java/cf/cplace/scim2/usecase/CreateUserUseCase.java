package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface CreateUserUseCase {

    @Nonnull
    UserResource createUser(@Nonnull UserResource user);
}
