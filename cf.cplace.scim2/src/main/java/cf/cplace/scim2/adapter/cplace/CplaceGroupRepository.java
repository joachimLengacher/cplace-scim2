package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Group;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.GroupRepository;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CplaceGroupRepository implements GroupRepository {

    private static final Logger log = LoggerFactory.getLogger(CplaceGroupRepository.class);

    private final int maxResults;

    public CplaceGroupRepository(int maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    @Override
    public ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest) {
        Preconditions.checkNotNull(searchRequest);
        log.debug("Finding groups that match {}...", searchRequest);
        int fetchCount = searchRequest.getCount() != null ? searchRequest.getCount() : maxResults;

        // TODO: apply search filters

        final List<GroupResource> resources = StreamSupport.stream(Group.SCHEMA.getEntities().spliterator(), false)
                .map(this::toGroupResource).collect(Collectors.toList());
        log.debug("Found {} matching groups", resources.size());
        return new ListResponse<>(resources.size(), resources, 0, fetchCount);
    }

    @Nonnull
    @Override
    public GroupResource findById(@Nonnull String id) {
        Preconditions.checkNotNull(id);
        log.debug("Finding group with id='{}'...", id);
        return toGroupResource(Group.SCHEMA.getEntityNotNull(id));
    }

    @Nonnull
    @Override
    public GroupResource create(@Nonnull GroupResource group) {
        Preconditions.checkNotNull(group);
        log.debug("Creating group with name='{}'...", group.getDisplayName());
        final Group cplaceGroup = Group.SCHEMA.createWritableEntity();
        mapGroupResourceToCplaceGroup(group, cplaceGroup);
        persist(cplaceGroup);

        log.debug("Successfully created group with name='{}'. Id is now '{}'.", cplaceGroup.getName(), cplaceGroup.getId());
        return toGroupResource(cplaceGroup);
    }

    @Nonnull
    @Override
    public GroupResource update(@Nonnull GroupResource groupResource) {
        Preconditions.checkNotNull(groupResource);
        log.debug("Updating group with id={}, name='{}'...", groupResource.getId(), groupResource.getDisplayName());
        final Group group = Group.SCHEMA.getEntityNotNull(groupResource.getId()).createWritableCopy();
        mapGroupResourceToCplaceGroup(groupResource, group);
        persist(group);
        log.debug("Successfully updated group with id='{}'.", group.getId());
        return toGroupResource(group);
    }

    @Nonnull
    @Override
    public GroupResource patch(@Nonnull String groupId, @Nonnull GroupResource groupResource) {
        Preconditions.checkNotNull(groupResource);
        Preconditions.checkNotNull(groupId);
        log.debug("Patching group with id={}...", groupId);
        final Group group = Group.SCHEMA.getEntityNotNull(groupId).createWritableCopy();
        mapNonEmptyGroupFieldsToCplaceGroup(groupResource, group);
        persist(group);
        log.debug("Successfully updated group with id='{}'.", group.getId());
        return toGroupResource(group);
    }

    private void mapGroupResourceToCplaceGroup(GroupResource group, Group cplaceGroup) {
        cplaceGroup._name().set(group.getDisplayName());
        cplaceGroup._isTechnicalGroup().set(false);
        cplaceGroup._administrators().set(Group.getAdminGroup());

        // TODO: update members
    }

    private void mapNonEmptyGroupFieldsToCplaceGroup(GroupResource groupResource, Group group) {
        if (isNotBlank(groupResource.getDisplayName())) {
            group._name().set(groupResource.getDisplayName());
        }

        // TODO: update members
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
