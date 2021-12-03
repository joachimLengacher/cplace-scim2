package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface FindUserUseCase {

    @Nonnull
    UserResource findById(@Nonnull String id);
}
