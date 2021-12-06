package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Person;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.NotImplementedException;
import cf.cplace.scim2.domain.UserRepository;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;
import java.util.List;

public class CplaceUserRepository implements UserRepository {

    @Nonnull
    @Override
    public UserResource create(@Nonnull UserResource user) {
        Preconditions.checkNotNull(user);
        final Person person = Person.SCHEMA.createWritableEntity();
        mapUserToPerson(user, person);
        persist(person);
        return toUser(person);
    }

    @Nonnull
    @Override
    public UserResource findById(@Nonnull String id) {
        return toUser(Person.SCHEMA.getEntityNotNull(id));
    }

    @Nonnull
    @Override
    public UserResource update(@Nonnull UserResource user) {
        Preconditions.checkNotNull(user);
        final Person person = Person.SCHEMA.getEntityNotNull(user.getId()).createWritableCopy();
        mapUserToPerson(user, person);
        persist(person);
        return toUser(person);
    }

    @Override
    public void deleteById(@Nonnull String id) {
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
