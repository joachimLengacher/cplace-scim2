package cf.cplace.scim2.domain;

import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface UserRepository {

    @Nonnull
    UserResource create(@Nonnull UserResource user);
}
