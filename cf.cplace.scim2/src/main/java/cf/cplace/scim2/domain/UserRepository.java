package cf.cplace.scim2.domain;

import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * A repository to perform CRUD operations with {@link UserResource}s.
 */
public interface UserRepository {

    @Nonnull
    UserResource create(@Nonnull UserResource user) throws IOException;

    /**
     * Updates a user completely. In contrast to the {@link #patch(String, UserResource)} method, this method will
     * overwrite all the fields of the user, even if they are {@code null}. Use this method in combination with the
     * {@link #findById(String)} method in order to get a complete user, update some fields and save it again.
     * Note that the user's ID must not be changed, as it will be used to find the existing user that must be updated.
     */
    @Nonnull
    UserResource update(@Nonnull UserResource user) throws IOException;

    /**
     * Updates a user partially. In contrast to {@link #update(UserResource)} this method will not overwrite fields
     * that are blank.
     */
    @Nonnull
    UserResource patch(@Nonnull String userId, @Nonnull UserResource partialUser) throws IOException;

    @Nonnull
    ListResponse<UserResource> find(@Nonnull SearchRequest searchRequest);

    @Nonnull
    UserResource findById(@Nonnull String id);

    void deleteById(@Nonnull String id);

}
