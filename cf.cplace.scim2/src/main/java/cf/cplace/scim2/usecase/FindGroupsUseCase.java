package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public interface FindGroupsUseCase {

    @Nonnull
    GroupResource findById(@Nonnull String id);

    @Nonnull
    ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest);
}
