package cf.cplace.scim2.adapter.cplace;

import cf.cplace.platform.assets.group.Person;
import cf.cplace.scim2.domain.ConflictException;
import cf.cplace.scim2.domain.NotImplementedException;
import cf.cplace.scim2.domain.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.exceptions.BadRequestException;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.filters.Filter;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.UserResource;
import com.unboundid.scim2.common.utils.FilterEvaluator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.base.Strings.nullToEmpty;

public class CplaceUserRepository implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(CplaceUserRepository.class);

    private final int maxResults;

    private final ObjectMapper mapper = new ObjectMapper();

    public CplaceUserRepository(int maxResults) {
        this.maxResults = maxResults;
    }

    @Nonnull
    @Override
    public UserResource create(@Nonnull UserResource user) {
        Preconditions.checkNotNull(user);
        log.debug("Creating person with login='{}'...", user.getUserName());
        final Person person = Person.SCHEMA.createWritableEntity();
        mapUserToPerson(user, person);
        persist(person);
        log.debug("Successfully created person with login='{}'. Id is now '{}'.", person._login().get(), person.getId());
        return toUser(person);
    }

    @Nonnull
    @Override
    public UserResource findById(@Nonnull String id) {
        Preconditions.checkNotNull(id);
        log.debug("Finding person with id='{}'...", id);
        return toUser(Person.SCHEMA.getEntityNotNull(id));
    }

    @Nonnull
    @Override
    public ListResponse<UserResource> find(@Nonnull SearchRequest searchRequest) {
        Preconditions.checkNotNull(searchRequest);
        log.debug("Finding persons that match {}...", searchRequest);
        int fetchCount = searchRequest.getCount() != null ? searchRequest.getCount() : maxResults;

        // TODO: implement paging


        final List<UserResource> resources = StreamSupport.stream(Person.SCHEMA.getEntities().spliterator(), false)
                .map(this::toUser)
                .filter(userResource -> needToFilter(searchRequest.getFilter(), userResource))
                .collect(Collectors.toList());

        log.debug("Found {} matching persons", resources.size());
        return   new ListResponse<>(resources.size(), resources, 0, fetchCount);
    }

    private boolean needToFilter(String filter, UserResource userResource) {
        try {
            return filter == null || matchesFilter(Filter.fromString(filter), userResource);
        } catch (BadRequestException e) {
            throw new cf.cplace.scim2.domain.BadRequestException(String.format("Invalid filter: '%s'", filter));
        }
    }

    @Nonnull
    @Override
    public UserResource update(@Nonnull UserResource user) {
        Preconditions.checkNotNull(user);
        log.debug("Updating person with id={}, login='{}'...", user.getId(), user.getUserName());
        final Person person = Person.SCHEMA.getEntityNotNull(user.getId()).createWritableCopy();
        mapUserToPerson(user, person);
        persist(person);
        log.debug("Successfully updated person with id='{}'.", person.getId());
        return toUser(person);
    }

    @Override
    public void deleteById(@Nonnull String id) {
        Preconditions.checkNotNull(id);
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
        person._name().set(NameConverter.toCplaceName(user.getName()));
        person._hasBeenDisabled().set(user.getActive() != null && !user.getActive());
        person._password().setHash(passwordOf(user));
        person._locale().set(user.getPreferredLanguage());
        copyPhoto(user, person);
        // TODO: potentially more to do here
    }

    private void copyPhoto(UserResource user, Person person) {
        try {
            if(hasPhoto(user)) {
                updatePhoto(person, photoOf(user));
            } else {
                // TODO: delete photo in this case or just keep the one we might already have?
            }
        } catch (IOException e) {
            log.error("Failed to set image for user '" + user.getUserName() + "'", e);
        }
    }

    private boolean hasPhoto(UserResource user) {
        return user.getPhotos() != null && user.getPhotos().size() > 0;
    }

    private void updatePhoto(Person person, BufferedImage image) throws IOException {
        if (image != null) {
            ImageIO.write(image, "PNG", thumbnailFileOf(person));
        }
    }

    private File thumbnailFileOf(Person person) throws IOException {
        final File thumbnailFile = person.getThumbnailFile();
        thumbnailFile.getParentFile().mkdirs();
        thumbnailFile.createNewFile();
        return thumbnailFile;
    }

    private BufferedImage photoOf(UserResource user) throws IOException {
        final URI photoUrl = user.getPhotos().get(0).getValue();
        return ImageIO.read(photoUrl.toURL());
    }

    private String passwordOf(UserResource user) {
        String password = user.getPassword();
        if (StringUtils.isBlank(password)) {
            return Person.randomPassword();
        }
        return password;
    }

    private UserResource toUser(Person person) {
        UserResource user = new UserResource()
                .setUserName(person._login().get())
                .setName(NameConverter.toScimName(person.getName()));
        user.setId(person.getId());
        user.setActive(person.isActiveAccount());
        user.setEmails(List.of(new Email()
                .setPrimary(true)
                .setValue(person._login().get())));
        user.setPassword(person._password().getHash());
        // TODO: more to do here
       return user;
    }

    private boolean matchesFilter(Filter filter, UserResource userResource) {
        try {
            return FilterEvaluator.evaluate(filter, toJsonNode(userResource));
        } catch (ScimException e) {
            throw new cf.cplace.scim2.domain.BadRequestException(e.getMessage());
        }
    }

    private JsonNode toJsonNode(UserResource user) {
        return mapper.convertValue(user, JsonNode.class);
    }

}
