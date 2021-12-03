package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Person;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.UserRepository;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.Name;
import com.unboundid.scim2.common.types.UserResource;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

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

    private void persist(Person person) {
        try {
            person.persist();
        } catch (IllegalStateException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    private void mapUserToPerson(UserResource user, Person person) {
        person._login().set(user.getUserName());
        person._name().set(createName(user.getName()));
        // TODO: more to do here
    }

    private UserResource toUser(Person person) {
        UserResource user = new UserResource().setUserName(person._login().get())
                .setName(new Name().setGivenName(person.getName()));
        user.setId(person.getId());
        // TODO: more to do here
       return user;
    }

    private String createName(Name name) {
        if (name == null) {
            return "";
        }
        return StringUtils.trimToEmpty(name.getGivenName()) + " " + StringUtils.trimToEmpty(name.getFamilyName());
    }
}
