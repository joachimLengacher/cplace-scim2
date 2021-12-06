package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Group;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.GroupRepository;
import com.google.common.base.Preconditions;
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
                .map(this::toGroupResource).collect(Collectors.toList());
        return new ListResponse<>(resources.size(), resources, 0, fetchCount);
    }

    @Nonnull
    @Override
    public GroupResource findById(@Nonnull String id) {
        return toGroupResource(Group.SCHEMA.getEntityNotNull(id));
    }

    @Nonnull
    @Override
    public GroupResource create(@Nonnull GroupResource group) {
        Preconditions.checkNotNull(group);
        final Group cplaceGroup = Group.SCHEMA.createWritableEntity();
        mapGroupResourceToCplaceGroup(group, cplaceGroup);
        persist(cplaceGroup);
        return toGroupResource(cplaceGroup);
    }

    @Nonnull
    @Override
    public GroupResource update(@Nonnull GroupResource groupResource) {
        Preconditions.checkNotNull(groupResource);
        final Group group = Group.SCHEMA.getEntityNotNull(groupResource.getId()).createWritableCopy();
        mapGroupResourceToCplaceGroup(groupResource, group);
        persist(group);
        return toGroupResource(group);
    }

    private void mapGroupResourceToCplaceGroup(GroupResource group, Group cplaceGroup) {
        cplaceGroup._name().set(group.getDisplayName());
        cplaceGroup._isTechnicalGroup().set(false);
        cplaceGroup._administrators().set(Group.getAdminGroup());
    }

    private void persist(Group cplaceGroup) {
        try {
            cplaceGroup.persist();
        } catch (IllegalStateException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    private GroupResource toGroupResource(Group cplaceGroup) {
        GroupResource group = new GroupResource().setDisplayName(cplaceGroup.getName());
        group.setId(cplaceGroup.getId());
        return group;
    }

}
