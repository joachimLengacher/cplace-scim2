package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface UpdateUserUseCase {

    @Nonnull
    UserResource updateUser(@Nonnull UserResource user);
}
