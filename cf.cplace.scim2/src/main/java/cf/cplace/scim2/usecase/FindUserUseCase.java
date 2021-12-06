package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public interface FindUserUseCase {

    @Nonnull
    UserResource findById(@Nonnull String id);

    @Nonnull
    ListResponse<UserResource>  find(@Nonnull SearchRequest searchRequest);
}
