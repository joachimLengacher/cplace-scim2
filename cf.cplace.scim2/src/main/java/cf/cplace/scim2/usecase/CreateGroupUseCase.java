package cf.cplace.scim2.usecase;

import com.unboundid.scim2.common.types.GroupResource;

import javax.annotation.Nonnull;

public interface CreateGroupUseCase {

    @Nonnull
    GroupResource createGroup(@Nonnull GroupResource group);
}
