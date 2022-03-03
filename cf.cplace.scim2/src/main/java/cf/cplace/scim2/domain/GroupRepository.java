package cf.cplace.scim2.domain;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public interface GroupRepository {

    @Nonnull
    ListResponse<GroupResource> find(@Nonnull SearchRequest searchRequest);

    @Nonnull
    GroupResource findById(@Nonnull String id);

    @Nonnull
    GroupResource create(@Nonnull GroupResource group);

    /**
     * Updates a group completely. In contrast to the {@link #patch(String, GroupResource)} method, this method will
     * overwrite all the fields of the group, even if they are {@code null}. Use this method in combination with the
     * {@link #findById(String)} method in order to get a complete group, update some fields and save it again.
     * Note that the group's ID must not be changed, as it will be used to find the existing group that must be updated.
     */
    @Nonnull
    GroupResource update(@Nonnull GroupResource groupResource);

    /**
     * Updates a group partially. In contrast to {@link #update(GroupResource)} this method will not overwrite fields
     * that are blank.
     */
    @Nonnull
    GroupResource patch(@Nonnull String groupId, @Nonnull GroupResource groupResource);
}
