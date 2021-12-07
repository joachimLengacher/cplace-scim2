package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Person;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.NotImplementedException;
import cf.cplace.scim2.domain.UserRepository;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CplaceUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(CplaceUserRepository.class);

    private final int maxResults;

    public CplaceUserRepository(int maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    @Override
    public UserResource create(@Nonnull UserResource user) {
        log.debug("Creating person with login='{}'...", user.getUserName());
        Preconditions.checkNotNull(user);
        final Person person = Person.SCHEMA.createWritableEntity();
        mapUserToPerson(user, person);
        persist(person);
        log.debug("Successfully created person with login='{}'. Id is now '{}'.", person._login().get(), person.getId());
        return toUser(person);
    }

    @Nonnull
    @Override
    public UserResource findById(@Nonnull String id) {
        log.debug("Finding person with id='{}'...", id);
        return toUser(Person.SCHEMA.getEntityNotNull(id));
    }

    @Nonnull
    @Override
    public ListResponse<UserResource> find(@Nonnull SearchRequest searchRequest) {
        log.debug("Finding persons that match {}...", searchRequest);
        int fetchCount = searchRequest.getCount() != null ? searchRequest.getCount() : maxResults;

        // TODO: apply search filters

        final List<UserResource> resources = StreamSupport.stream(Person.SCHEMA.getEntities().spliterator(), false)
                .map(this::toUser).collect(Collectors.toList());
        log.debug("Found {} matching persons", resources.size());
        return new ListResponse<>(resources.size(), resources, 0, fetchCount);
    }

    @Nonnull
    @Override
    public UserResource update(@Nonnull UserResource user) {
        log.debug("Updating person with id={}, login='{}'...", user.getId(), user.getUserName());
        Preconditions.checkNotNull(user);
        final Person person = Person.SCHEMA.getEntityNotNull(user.getId()).createWritableCopy();
        mapUserToPerson(user, person);
        persist(person);
        log.debug("Successfully updated person with id='{}'.", person.getId());
        return toUser(person);
    }

    @Override
    public void deleteById(@Nonnull String id) {
        log.warn("Deleting person entities not implemented!");
        throw new NotImplementedException("Deleting users is not implemented.");
    }

    private void persist(Person person) {
        try {
            person.persist();
        } catch (IllegalStateException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    private void mapUserToPerson(UserResource user, Person person) {
        person._login().set(user.getUserName());
        person._name().set(user.getDisplayName());
        person._hasBeenDisabled().set(!user.getActive());
        // TODO: more to do here
    }

    private UserResource toUser(Person person) {
        UserResource user = new UserResource().setUserName(person._login().get())
                .setDisplayName(person.getName());
        user.setId(person.getId());
        user.setActive(person.isActiveAccount());
        user.setEmails(List.of(new Email()
                .setPrimary(true)
                .setValue(person._login().get())));
        // TODO: more to do here
       return user;
    }
}
