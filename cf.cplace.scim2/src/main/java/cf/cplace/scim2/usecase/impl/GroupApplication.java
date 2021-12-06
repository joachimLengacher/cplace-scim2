package cf.cplace.scim2.usecase.impl;

import cf.cplace.scim2.domain.GroupRepository;
import cf.cplace.scim2.usecase.CreateGroupUseCase;
import cf.cplace.scim2.usecase.FindGroupsUseCase;
import cf.cplace.scim2.usecase.UpdateGroupUseCase;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public class GroupApplication implements FindGroupsUseCase, CreateGroupUseCase, UpdateGroupUseCase {

    private final GroupRepository groupRepository;

    public GroupApplication(GroupRepository groupRepository) {
        this.groupRepository = Preconditions.checkNotNull(groupRepository);
    }

    @Nonnull
    @Override
    public ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest) {
        return groupRepository.find(searchRequest);
    }

    @Nonnull
    @Override
    public GroupResource findById(@Nonnull String id) {
        return groupRepository.findById(id);
    }

    @Nonnull
    @Override
    public GroupResource createGroup(@Nonnull GroupResource group) {
        return groupRepository.create(group);
    }

    @Nonnull
    @Override
    public GroupResource updateGroup(@Nonnull GroupResource group) {
        return groupRepository.update(group);
    }
}
