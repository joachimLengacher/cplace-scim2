package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public interface UpdateGroupUseCase {

    @Nonnull
    GroupResource updateGroup(@Nonnull GroupResource group);
}
