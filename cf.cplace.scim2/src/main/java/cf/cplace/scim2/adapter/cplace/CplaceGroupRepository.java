package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Group;
import cf.cplace.scim2.domain.GroupRepository;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CplaceGroupRepository implements GroupRepository {

    private final int maxResults;

    public CplaceGroupRepository(int maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    @Override
    public ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest) {

        int fetchCount = searchRequest.getCount() != null ? searchRequest.getCount() : maxResults;

        // TODO: apply search filters

        final List<GroupResource> resources = StreamSupport.stream(Group.SCHEMA.getEntities().spliterator(), false)
                .map(this::toGroup).collect(Collectors.toList());
        return new ListResponse<>(resources.size(), resources, 0, fetchCount);
    }

    private GroupResource toGroup(Group cplaceGroup) {
        GroupResource group = new GroupResource().setDisplayName(cplaceGroup.getName());
        group.setId(cplaceGroup.getId());
        return group;
    }

}
