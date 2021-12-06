package cf.cplace.scim2.domain;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public interface GroupRepository {

    @Nonnull
    ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest);
}
