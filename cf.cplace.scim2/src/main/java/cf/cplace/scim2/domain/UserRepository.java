package cf.cplace.scim2.domain;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface UserRepository {

    @Nonnull
    UserResource create(@Nonnull UserResource user);

    @Nonnull
    UserResource update(@Nonnull UserResource user);

    @Nonnull
    ListResponse<UserResource> find(@Nonnull SearchRequest searchRequest);

    @Nonnull
    UserResource findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}
